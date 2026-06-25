package com.hx.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 退出登录 Servlet - 处理用户注销请求
 * 
 * 作用：
 * 1. 销毁用户 Session，清除登录状态
 * 2. 重定向到登录页面
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /logout
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 获取当前 Session 对象
 * 4. 移除 currentUser 属性并销毁 Session
 * 5. 重定向到登录页面
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    /**
     * GET 请求处理方法 - 处理退出登录请求
     * 
     * 处理流程：
     * 1. 获取当前请求的 Session 对象
     * 2. 移除"currentUser"属性，清除用户信息
     * 3. 调用 invalidate() 方法销毁整个 Session
     * 4. 重定向到登录页面
     * 
     * @param request HttpServletRequest 请求对象
     * @param response HttpServletResponse 响应对象，用于向客户端返回响应
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 获取当前请求的 Session 对象
        HttpSession session = request.getSession();
        // 移除"currentUser"属性，清除存储的用户信息
        session.removeAttribute("currentUser");
        // 销毁整个 Session 对象，释放服务器资源
        // 执行后，所有 Session 属性都会被清除
        session.invalidate();
        
        // 重定向到登录页面
        // getContextPath() 获取项目上下文路径，避免硬编码
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
    
    /**
     * POST 请求处理方法
     * 作用：处理 POST 方式的退出登录请求
     * 实现：直接调用 doGet 方法，统一处理逻辑
     * 
     * @param request HttpServletRequest 请求对象
     * @param response HttpServletResponse 响应对象
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 调用 doGet 方法处理 POST 请求
        doGet(request, response);
    }
}
