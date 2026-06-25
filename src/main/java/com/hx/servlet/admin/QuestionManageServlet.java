package com.hx.servlet.admin;

import com.hx.dao.QuestionDAO;
import com.hx.entity.Question;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 试题管理 Servlet - 处理管理员查看和管理试题列表的请求
 * 
 * 作用：
 * 1. 查询数据库中所有试题
 * 2. 将试题列表传递给请求属性
 * 3. 转发到试题管理页面进行展示
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /admin/questionList
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 调用 QuestionDAO 的 findAll 方法获取所有试题
 * 4. 将试题列表存入 request 属性，键名为"questionList"
 * 5. 使用 RequestDispatcher 转发到 questionList.jsp 显示
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/admin/questionList")
public class QuestionManageServlet extends HttpServlet {
    
    // 创建 QuestionDAO 实例，用于访问试题数据
    private QuestionDAO questionDAO = new QuestionDAO();
    
    /**
     * GET 请求处理方法 - 处理查看试题列表请求
     * 
     * 处理流程：
     * 1. 调用 DAO 层的 findAll 方法查询所有试题
     * 2. 将试题列表存储到 request 属性中，供 JSP 页面使用
     * 3. 使用 RequestDispatcher 转发到试题管理页面（questionList.jsp）
     * 4. JSP 页面通过 EL 表达式或 JSTL 遍历显示试题列表
     * 
     * @param request HttpServletRequest 请求对象
     * @param response HttpServletResponse 响应对象，用于向客户端返回响应
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 调用 DAO 层的 findAll 方法，查询数据库中所有试题
        // 返回一个 List<Question> 集合，包含所有试题对象
        List<Question> questionList = questionDAO.findAll();
        
        // 将试题列表存储到 request 作用域中
        // 这样在 JSP 页面中可以通过 request.getAttribute("questionList") 获取
        // 或使用 EL 表达式 ${questionList} 访问
        request.setAttribute("questionList", questionList);
        
        // 使用 RequestDispatcher 将请求转发到试题管理页面
        // 转发是在服务器内部跳转，URL 不会改变，可以共享 request 中的数据
        request.getRequestDispatcher("/admin/questionList.jsp").forward(request, response);
    }
    
    /**
     * POST 请求处理方法
     * 作用：处理 POST 方式的试题管理请求
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
