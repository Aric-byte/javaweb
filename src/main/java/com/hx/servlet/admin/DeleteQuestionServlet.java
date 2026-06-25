package com.hx.servlet.admin;

import com.hx.dao.QuestionDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除试题 Servlet - 处理管理员删除试题的请求
 * 
 * 作用：
 * 1. 接收要删除的试题 ID
 * 2. 调用 DAO 层从数据库中删除指定试题
 * 3. 根据删除结果跳转到相应页面
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /admin/deleteQuestion
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 从请求参数中获取 questionId
 * 4. 调用 QuestionDAO 的 delete 方法执行删除
 * 5. 删除成功重定向到试题列表，失败重定向并附带错误参数
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/admin/deleteQuestion")
public class DeleteQuestionServlet extends HttpServlet {
    
    // 创建 QuestionDAO 实例，用于访问试题数据
    private QuestionDAO questionDAO = new QuestionDAO();
    
    /**
     * POST 请求处理方法 - 处理删除试题请求
     * 
     * 处理流程：
     * 1. 从请求参数中获取试题 ID（questionId）
     * 2. 将字符串类型的 questionId 转换为整数
     * 3. 调用 DAO 层执行删除操作
     * 4. 删除成功：重定向到试题列表页面
     * 5. 删除失败：重定向到试题列表页面，并附带错误参数 error=delete_failed
     * 
     * @param request HttpServletRequest 请求对象，包含要删除的试题 ID
     * @param response HttpServletResponse 响应对象，用于向客户端返回响应
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 从请求参数中获取试题 ID
        // 该参数通常来自表单提交或链接中的查询参数
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        
        // 调用 DAO 层的删除方法，执行删除操作
        boolean success = questionDAO.delete(questionId);
        
        if (success) {
            // 删除成功：重定向到试题列表页面
            response.sendRedirect(request.getContextPath() + "/admin/questionList");
        } else {
            // 删除失败：重定向到试题列表页面，并附带错误参数
            // error=delete_failed 参数可用于在页面显示错误提示
            response.sendRedirect(request.getContextPath() + "/admin/questionList?error=delete_failed");
        }
    }
    
    /**
     * GET 请求处理方法
     * 作用：处理 GET 方式的删除试题请求
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
