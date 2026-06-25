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
 * 更新用户信息 Servlet - 处理用户修改个人信息的请求
 * 
 * 作用：
 * 1. 接收用户提交的更新信息（用户名、密码、性别、邮箱）
 * 2. 查询原用户信息并验证用户是否存在
 * 3. 更新用户对象各属性（密码只有输入新值时才修改）
 * 4. 调用 DAO 层执行更新操作
 * 5. 更新成功后同步 Session 中的用户信息
 * 6. 返回修改结果到个人信息页面
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /updateUser
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 从请求参数中获取 userId 和各字段信息
 * 4. 调用 UsersDAO 的 findById 方法查询原用户
 * 5. 更新 Users 对象属性，密码字段特殊处理
 * 6. 调用 UsersDAO 的 update 方法执行更新
 * 7. 更新成功后重新设置 Session 中的 currentUser
 * 8. 转发回 profile.jsp 显示结果消息
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
    
    // 创建 UsersDAO 实例，用于访问用户数据
    private UsersDAO usersDAO = new UsersDAO();
    
    /**
     * POST 请求处理方法 - 处理更新用户信息表单提交
     * 
     * 处理流程：
     * 1. 设置请求编码为 UTF-8，防止中文乱码
     * 2. 设置响应类型为 text/html，编码为 UTF-8
     * 3. 从请求参数中获取 userId、userName、password、sex、email
     * 4. 调用 DAO 层查询原用户信息，验证用户是否存在
     * 5. 用户不存在则设置错误消息并转发回页面
     * 6. 更新用户对象各属性（密码只有非空时才修改）
     * 7. 调用 DAO 层执行更新操作
     * 8. 更新成功：同步 Session 中的用户信息，设置成功消息
     * 9. 更新失败：设置失败消息
     * 10. 转发到个人信息页面显示结果
     * 
     * @param request HttpServletRequest 请求对象，包含用户提交的更新信息
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
        
        // 从请求参数中获取用户 ID
        int userId = Integer.parseInt(request.getParameter("userId"));
        // 从请求参数中获取新用户名
        String userName = request.getParameter("userName");
        // 从请求参数中获取新密码（可能为空）
        String password = request.getParameter("password");
        // 从请求参数中获取新性别
        String sex = request.getParameter("sex");
        // 从请求参数中获取新邮箱
        String email = request.getParameter("email");
        
        // 调用 DAO 层的 findById 方法，查询原用户信息
        // 用于验证用户是否存在
        Users user = usersDAO.findById(userId);
        if (user == null) {
            // 用户不存在：设置错误消息
            request.setAttribute("message", "用户不存在！");
            // 转发回个人信息页面
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            return;
        }
        
        // 更新用户对象的各属性
        // 设置新用户名
        user.setUserName(userName);
        // 密码特殊处理：只有当用户输入了新密码时才修改
        // 如果密码框为空（trim 后为空字符串），保持原密码不变
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password); // 只有输入新密码才修改
        }
        // 设置新性别
        user.setSex(sex);
        // 设置新邮箱
        user.setEmail(email);
        
        // 调用 DAO 层的 update 方法，执行更新操作
        boolean success = usersDAO.update(user);
        
        if (success) {
            // 更新成功：同步 Session 中的用户信息
            // 因为用户对象已修改，需要更新 Session 中存储的对象，保持一致性
            request.getSession().setAttribute("currentUser", user);
            // 设置成功消息
            request.setAttribute("message", "修改成功！");
            // 转发到个人信息页面显示结果
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        } else {
            // 更新失败：设置失败消息
            request.setAttribute("message", "修改失败！");
            // 转发到个人信息页面显示结果
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        }
    }
    
    /**
     * GET 请求处理方法
     * 作用：处理 GET 方式的更新用户信息请求
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
