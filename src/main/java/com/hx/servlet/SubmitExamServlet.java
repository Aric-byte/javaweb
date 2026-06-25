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
import java.util.Map;

/**
 * 提交考试 Servlet - 处理试卷提交和在线阅卷
 * 
 * 作用：
 * 1. 从 Session 中获取试题信息
 * 2. 接收用户提交的答案
 * 3. 逐题比对用户答案与正确答案
 * 4. 计算总分（每题分数 = 100/题目数量）
 * 5. 清除 Session 中的试题数据
 * 6. 将成绩传递给成绩展示页面
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /submitExam
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 从 Session 获取"examQuestions"属性（试题列表）
 * 4. 遍历试题，通过 request.getParameter 获取用户答案
 * 5. 比对答案并累加分数
 * 6. 移除 Session 中的试题数据
 * 7. 使用 request.setAttribute 设置成绩，转发到 score.jsp
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/submitExam")
public class SubmitExamServlet extends HttpServlet {
    
    // 创建 QuestionDAO 实例，用于访问试题数据
    private QuestionDAO questionDAO = new QuestionDAO();
    
    /**
     * POST 请求处理方法 - 处理试卷提交和批改
     * 
     * 处理流程：
     * 1. 设置请求编码为 UTF-8，防止中文乱码
     * 2. 获取当前 Session 对象
     * 3. 从 Session 中取出试题列表（"examQuestions"）
     * 4. 验证试题列表是否为空，为空则重定向到首页
     * 5. 初始化分数变量，计算每题分值（100/题目数）
     * 6. 遍历试题，获取用户答案并与正确答案比对
     * 7. 答案正确则累加该题分数
     * 8. 清除 Session 中的试题数据
     * 9. 设置总成绩和题目数量属性
     * 10. 转发到 score.jsp 显示成绩
     * 
     * @param request HttpServletRequest 请求对象，包含用户提交的答案
     * @param response HttpServletResponse 响应对象，用于向客户端返回响应
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置请求编码为 UTF-8，解决中文乱码问题
        request.setCharacterEncoding("UTF-8");
        // 获取当前 Session 对象
        HttpSession session = request.getSession();
        
        // 从 Session 中获取试题列表
        // 这些试题是在开始考试时存入 Session 的
        List<Question> questions = (List<Question>) session.getAttribute("examQuestions");
        
        // 验证试题列表是否存在或是否为空
        if (questions == null || questions.isEmpty()) {
            // 没有找到试题，重定向到首页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // 初始化总分为 0
        int score = 0;
        // 获取题目总数
        int totalQuestions = questions.size();
        // 计算每题分数：总分 100 分，平均分配到每道题
        int scorePerQuestion = 100 / totalQuestions;
        
        // 遍历所有试题，逐一比对答案
        for (Question question : questions) {
            // 从请求参数中获取用户答案
            // 答案参数名为"answer_" + 试题 ID，与 exam.jsp 中的表单字段名对应
            String userAnswer = request.getParameter("answer_" + question.getQuestionId());
            // 从试题对象中获取正确答案
            String correctAnswer = question.getAnswer();
            
            // 比对用户答案与正确答案
            // 如果两者相等（字符串比较用 equals），说明回答正确
            if (correctAnswer.equals(userAnswer)) {
                // 答案正确，累加该题分数
                score += scorePerQuestion;
            }
        }
        
        // 清除 Session 中的试题数据
        // 交卷后不再需要试题信息，释放服务器资源
        session.removeAttribute("examQuestions");
        
        // 将成绩信息设置到 request 属性中，供成绩页面使用
        request.setAttribute("score", score);  // 总分
        request.setAttribute("totalQuestions", totalQuestions);  // 题目总数
        
        // 使用请求转发到成绩展示页面（score.jsp）
        // 转发可以保留 request 中的 score 和 totalQuestions 属性
        request.getRequestDispatcher("/score.jsp").forward(request, response);
    }
    
    /**
     * GET 请求处理方法
     * 作用：处理 GET 方式的提交考试请求
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
