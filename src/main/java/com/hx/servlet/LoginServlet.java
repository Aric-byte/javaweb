package com.hx.servlet;

import com.hx.dao.UsersDAO;
import com.hx.entity.Users;
import com.hx.filter.LoginSecurityFilter;
import com.hx.util.CaptchaUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录 Servlet - 处理用户登录请求
 * 
 * 作用：
 * 1. 接收用户提交的用户名和密码
 * 2. 调用 DAO 层验证用户身份
 * 3. 登录成功则将用户信息存入 Session，实现会话管理
 * 4. 根据用户角色（管理员/普通用户）跳转到不同页面
 * 5. 登录失败则返回错误信息
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /login
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 从请求参数中获取用户名和密码
 * 4. 调用 UsersDAO 的 login 方法进行验证
 * 5. 使用 HttpSession 存储登录成功的用户信息
 * 6. 根据角色进行重定向或请求转发
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    // 创建 UsersDAO 实例，用于访问用户数据
    private UsersDAO usersDAO = new UsersDAO();
    
    /**
     * POST 请求处理方法 - 处理登录表单提交
     * 
     * 处理流程：
     * 1. 设置请求编码为 UTF-8，防止中文乱码
     * 2. 设置响应类型为 text/html，编码为 UTF-8
     * 3. 从请求参数中获取用户名和密码
     * 4. 调用 DAO 层进行登录验证
     * 5. 登录成功：创建 Session 存储用户信息，根据角色重定向到不同页面
     * 6. 登录失败：设置错误消息，转发回登录页面
     * 
     * @param request HttpServletRequest 请求对象，包含客户端提交的数据
     * @param response HttpServletResponse 响应对象，用于向客户端返回响应
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置请求编码为 UTF-8，解决中文乱码问题
        request.setCharacterEncoding("UTF-8");
        // 设置响应内容类型和编码
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取客户端IP地址
        String clientIP = getClientIP(request);
        
        // 从请求参数中获取用户名
        String userName = request.getParameter("userName");
        // 从请求参数中获取密码
        String password = request.getParameter("password");
        // 从请求参数中获取算术题答案
        String captchaAnswer = request.getParameter("captchaAnswer");
        
        // 获取session中的正确答案
        HttpSession session = request.getSession();
        String correctAnswer = (String) session.getAttribute("captchaAnswer");
        
        // 验证算术题答案
        if (!CaptchaUtil.verifyAnswer(captchaAnswer, correctAnswer)) {
            // 算术题答案错误，记录失败尝试
            LoginSecurityFilter.recordFailedLogin(clientIP);
            request.setAttribute("message", "算术题答案错误，请重新计算！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        // 清除session中的验证码答案，防止重复使用
        session.removeAttribute("captchaAnswer");
        
        // 调用 DAO 层的登录方法，验证用户名和密码
        Users user = usersDAO.login(userName, password);
        
        if (user != null) {
            // 登录成功，清除登录尝试记录
            LoginSecurityFilter.clearLoginAttempts(clientIP);
            
            // 获取当前请求的 Session 对象（如果不存在则创建一个新的）session的第一次创建
            // 将用户对象存储到 Session 中，键名为"currentUser"
            // 这样在后续请求中可以通过 session.getAttribute("currentUser") 获取用户信息
            session.setAttribute("currentUser", user);
            
            // 根据用户角色跳转到不同的页面
            if ("admin".equals(user.getRole())) {
                // 管理员角色：重定向到试题管理页面
                // getContextPath() 获取项目上下文路径，避免硬编码
                response.sendRedirect(request.getContextPath() + "/admin/questionList");
            } else {
                // 普通用户角色：重定向到首页
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } else {
            // 登录失败：用户名或密码错误，记录失败尝试
            LoginSecurityFilter.recordFailedLogin(clientIP);
            
            // 设置错误消息属性
            request.setAttribute("message", "用户名或密码错误！");
            // 使用请求转发回到登录页面，保留 request 中的 message 属性
            // 转发是在服务器内部跳转，URL 不会改变
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    
    /**
     * 获取客户端真实IP地址
     */
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于多个代理的情况，第一个IP为客户端真实IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
    
    /**
     * GET 请求处理方法
     * 作用：处理 GET 方式的登录请求（通常用于表单初次加载）
     * 实现：直接调用 doPost 方法，统一处理逻辑
     * 
     * @param request HttpServletRequest 请求对象
     * @param response HttpServletResponse 响应对象
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 调用 doPost 方法处理 GET 请求
        doPost(request, response);
    }
}
