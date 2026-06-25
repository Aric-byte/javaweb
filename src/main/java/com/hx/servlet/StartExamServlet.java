package com.hx.servlet;

import com.hx.dao.QuestionDAO;
import com.hx.entity.Question;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 开始考试 Servlet - 处理开始考试请求，随机组卷
 * 
 * 作用：
 * 1. 检查用户登录状态，未登录则跳转到登录页
 * 2. 从题库中随机抽取指定数量的试题（默认 4 题）
 * 3. 将试题存储到 Session 中，供考试页面使用
 * 4. 跳转到考试页面显示试题
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /startExam
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 通过 Session 检查用户是否登录
 * 4. 调用 QuestionDAO 的 getRandomQuestions 方法随机抽题
 * 5. 将试题列表存入 Session，键名为"examQuestions"
 * 6. 重定向到 exam.jsp 显示试题
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/startExam")
public class StartExamServlet extends HttpServlet {
    
    // 创建 QuestionDAO 实例，用于访问试题数据
    private QuestionDAO questionDAO = new QuestionDAO();
    
    /**
     * GET 请求处理方法 - 处理开始考试请求
     * 
     * 处理流程：
     * 1. 获取当前 Session 对象
     * 2. 检查 Session 中是否存在"currentUser"属性（验证登录状态）
     * 3. 未登录则重定向到登录页面
     * 4. 已登录则调用 DAO 层随机获取 4 道试题
     * 5. 将试题列表存入 Session，供考试页面使用
     * 6. 重定向到考试页面（exam.jsp）
     * 
     * @param request HttpServletRequest 请求对象
     * @param response HttpServletResponse 响应对象，用于向客户端返回响应
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 获取当前 Session 对象
        HttpSession session = request.getSession();
        
        // 检查用户是否登录：判断 Session 中是否存在"currentUser"属性
        if (session.getAttribute("currentUser") == null) {
            // 未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 调用 DAO 层的 getRandomQuestions 方法，随机获取 4 道试题
        // 该方法会从题库中随机抽取，确保每次考试的题目不同
        List<Question> questions = questionDAO.getRandomQuestions(4);
        
        // 将试题列表存储到 Session 中
        // 这样在提交试卷时可以从 Session 中获取试题进行批改
        session.setAttribute("examQuestions", questions);
        
        // 重定向到考试页面
        // getContextPath() 获取项目上下文路径，避免硬编码
        response.sendRedirect(request.getContextPath() + "/exam.jsp");
    }
    
    /**
     * POST 请求处理方法
     * 作用：处理 POST 方式的开始考试请求
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
