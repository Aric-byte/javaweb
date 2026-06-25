package com.hx.servlet;

import com.hx.dao.UsersDAO;
import com.hx.entity.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注册 Servlet - 处理用户注册请求
 * 
 * 作用：
 * 1. 接收用户提交的注册信息（用户名、密码、性别、邮箱）
 * 2. 创建 Users 实体对象并设置默认角色为普通用户
 * 3. 调用 DAO 层将新用户信息插入数据库
 * 4. 根据注册结果跳转到相应页面
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /register
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 从请求参数中获取注册表单数据
 * 4. 封装 Users 对象，设置默认角色为"user"
 * 5. 调用 UsersDAO 的 register 方法执行插入
 * 6. 注册成功重定向到登录页，失败转发回注册页并显示错误
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    // 创建 UsersDAO 实例，用于访问用户数据
    private UsersDAO usersDAO = new UsersDAO();
    
    /**
     * POST 请求处理方法 - 处理注册表单提交
     * 
     * 处理流程：
     * 1. 设置请求编码为 UTF-8，防止中文乱码
     * 2. 设置响应类型为 text/html，编码为 UTF-8
     * 3. 从请求参数中获取用户名、密码、性别、邮箱
     * 4. 创建 Users 对象并设置各属性，角色默认为"user"
     * 5. 调用 DAO 层执行注册操作
     * 6. 注册成功：重定向到登录页，附带成功消息参数
     * 7. 注册失败：设置错误消息，转发回注册页面
     * 
     * @param request HttpServletRequest 请求对象，包含注册表单数据
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
        
        // 从请求参数中获取用户名
        String userName = request.getParameter("userName");
        // 从请求参数中获取密码
        String password = request.getParameter("password");
        // 从请求参数中获取性别
        String sex = request.getParameter("sex");
        // 从请求参数中获取邮箱
        String email = request.getParameter("email");
        
        // 创建用户对象
        Users user = new Users();
        // 设置用户名
        user.setUserName(userName);
        // 设置密码（明文存储，生产环境应加密）
        user.setPassword(password);
        // 设置性别
        user.setSex(sex);
        // 设置邮箱
        user.setEmail(email);
        // 设置角色为普通用户（默认值，不从前台传递）
        user.setRole("user");
        
        // 调用 DAO 层的注册方法，执行插入操作
        boolean success = usersDAO.register(user);
        
        if (success) {
            // 注册成功：重定向到登录页面
            // 使用 redirect 避免表单重复提交
            // msg=register_success 参数可用于在登录页显示成功提示
            response.sendRedirect(request.getContextPath() + "/login.jsp?msg=register_success");
        } else {
            // 注册失败：设置错误消息
            request.setAttribute("message", "注册失败，请重试！");
            // 使用请求转发回到注册页面，保留 message 属性
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
    
    /**
     * GET 请求处理方法
     * 作用：处理 GET 方式的注册请求
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
