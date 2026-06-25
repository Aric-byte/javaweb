package com.hx.servlet.admin;

import com.hx.dao.UsersDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除用户 Servlet - 处理管理员删除用户的请求
 * 
 * 作用：
 * 1. 接收要删除的用户 ID
 * 2. 调用 DAO 层从数据库中删除指定用户
 * 3. 根据删除结果跳转到相应页面
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /admin/deleteUser
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 从请求参数中获取 userId
 * 4. 调用 UsersDAO 的 delete 方法执行删除
 * 5. 删除成功重定向到用户列表，失败重定向并附带错误参数
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/admin/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    
    // 创建 UsersDAO 实例，用于访问用户数据
    private UsersDAO usersDAO = new UsersDAO();
    
    /**
     * POST 请求处理方法 - 处理删除用户请求
     * 
     * 处理流程：
     * 1. 设置请求编码为 UTF-8，防止中文乱码
     * 2. 从请求参数中获取用户 ID（userId）
     * 3. 将字符串类型的 userId 转换为整数
     * 4. 调用 DAO 层执行删除操作
     * 5. 删除成功：重定向到用户列表页面
     * 6. 删除失败：重定向到用户列表页面，并附带错误参数 error=delete_failed
     * 
     * @param request HttpServletRequest 请求对象，包含要删除的用户 ID
     * @param response HttpServletResponse 响应对象，用于向客户端返回响应
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置请求编码为 UTF-8，解决中文乱码问题
        request.setCharacterEncoding("UTF-8");
        
        // 从请求参数中获取用户 ID
        // 该参数通常来自表单提交或链接中的查询参数
        int userId = Integer.parseInt(request.getParameter("userId"));
        
        // 调用 DAO 层的删除方法，执行删除操作
        boolean success = usersDAO.delete(userId);
        
        if (success) {
            // 删除成功：重定向到用户列表页面
            response.sendRedirect(request.getContextPath() + "/admin/userList");
        } else {
            // 删除失败：重定向到用户列表页面，并附带错误参数
            // error=delete_failed 参数可用于在页面显示错误提示
            response.sendRedirect(request.getContextPath() + "/admin/userList?error=delete_failed");
        }
    }
    
    /**
     * GET 请求处理方法
     * 作用：处理 GET 方式的删除用户请求
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
