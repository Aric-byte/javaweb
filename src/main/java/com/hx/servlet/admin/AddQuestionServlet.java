package com.hx.servlet.admin;

import com.hx.dao.QuestionDAO;
import com.hx.entity.Question;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 添加试题 Servlet - 处理管理员添加新试题的请求
 * 
 * 作用：
 * 1. 接收管理员提交的试题信息（题干、四个选项、答案）
 * 2. 创建 Question 实体对象并填充数据
 * 3. 调用 DAO 层将试题插入数据库
 * 4. 根据添加结果跳转到相应页面
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /admin/addQuestion
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 从请求参数中获取试题各字段信息
 * 4. 封装 Question 对象
 * 5. 调用 QuestionDAO 的 add 方法执行插入
 * 6. 添加成功重定向到试题列表，失败转发回添加页面并显示错误
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/admin/addQuestion")
public class AddQuestionServlet extends HttpServlet {
    
    // 创建 QuestionDAO 实例，用于访问试题数据
    private QuestionDAO questionDAO = new QuestionDAO();
    
    /**
     * POST 请求处理方法 - 处理添加试题表单提交
     * 
     * 处理流程：
     * 1. 设置请求编码为 UTF-8，防止中文乱码
     * 2. 从请求参数中获取题干、选项 A、B、C、D 和答案
     * 3. 创建 Question 对象并设置各属性
     * 4. 调用 DAO 层执行添加操作
     * 5. 添加成功：重定向到试题列表页面
     * 6. 添加失败：设置错误消息，转发回添加试题页面
     * 
     * @param request HttpServletRequest 请求对象，包含试题表单数据
     * @param response HttpServletResponse 响应对象，用于向客户端返回响应
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置请求编码为 UTF-8，解决中文乱码问题
        request.setCharacterEncoding("UTF-8");
        
        // 从请求参数中获取题目标干
        String title = request.getParameter("title");
        // 从请求参数中获取选项 A 的内容
        String optionA = request.getParameter("optionA");
        // 从请求参数中获取选项 B 的内容
        String optionB = request.getParameter("optionB");
        // 从请求参数中获取选项 C 的内容
        String optionC = request.getParameter("optionC");
        // 从请求参数中获取选项 D 的内容
        String optionD = request.getParameter("optionD");
        // 从请求参数中获取正确答案（A/B/C/D）
        String answer = request.getParameter("answer");
        
        // 创建试题对象
        Question question = new Question();
        // 设置题干
        question.setTitle(title);
        // 设置选项 A
        question.setOptionA(optionA);
        // 设置选项 B
        question.setOptionB(optionB);
        // 设置选项 C
        question.setOptionC(optionC);
        // 设置选项 D
        question.setOptionD(optionD);
        // 设置正确答案
        question.setAnswer(answer);
        
        // 调用 DAO 层的添加方法，执行插入操作
        boolean success = questionDAO.add(question);
        
        if (success) {
            // 添加成功：重定向到试题列表页面
            // 使用 redirect 避免表单重复提交
            response.sendRedirect(request.getContextPath() + "/admin/questionList");
        } else {
            // 添加失败：设置错误消息
            request.setAttribute("message", "添加失败！");
            // 使用请求转发回到添加试题页面，保留 message 属性
            request.getRequestDispatcher("/admin/addQuestion.jsp").forward(request, response);
        }
    }
    
    /**
     * GET 请求处理方法
     * 作用：处理 GET 方式的添加试题请求，通常用于显示添加试题表单页面
     * 实现：转发到 addQuestion.jsp 页面
     * 
     * @param request HttpServletRequest 请求对象
     * @param response HttpServletResponse 响应对象
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 转发到添加试题页面，显示表单
        request.getRequestDispatcher("/admin/addQuestion.jsp").forward(request, response);
    }
}
