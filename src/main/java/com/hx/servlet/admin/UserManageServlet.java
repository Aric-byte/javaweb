package com.hx.servlet.admin;

import com.hx.dao.UsersDAO;
import com.hx.entity.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户管理 Servlet - 处理管理员查看和管理用户列表的请求
 * 
 * 作用：
 * 1. 查询数据库中所有用户
 * 2. 将用户列表传递给请求属性
 * 3. 转发到用户管理页面进行展示
 * 
 * 实现方法：
 * 1. 使用@WebServlet 注解配置 URL 映射路径为 /admin/userList
 * 2. 继承 HttpServlet，重写 doGet 和 doPost 方法
 * 3. 调用 UsersDAO 的 findAll 方法获取所有用户
 * 4. 将用户列表存入 request 属性，键名为"userList"
 * 5. 使用 RequestDispatcher 转发到 userList.jsp 显示
 * 
 * @author hx
 * @version 1.0
 */
@WebServlet("/admin/userList")
public class UserManageServlet extends HttpServlet {
    
    // 创建 UsersDAO 实例，用于访问用户数据
    private UsersDAO usersDAO = new UsersDAO();
    
    /**
     * GET 请求处理方法 - 处理查看用户列表请求
     * 
     * 处理流程：
     * 1. 调用 DAO 层的 findAll 方法查询所有用户
     * 2. 将用户列表存储到 request 属性中，供 JSP 页面使用
     * 3. 使用 RequestDispatcher 转发到用户管理页面（userList.jsp）
     * 4. JSP 页面通过 EL 表达式或 JSTL 遍历显示用户列表
     * 
     * @param request HttpServletRequest 请求对象
     * @param response HttpServletResponse 响应对象，用于向客户端返回响应
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 调用 DAO 层的 findAll 方法，查询数据库中所有用户
        // 返回一个 List<Users> 集合，包含所有用户对象
        List<Users> userList = usersDAO.findAll();
        
        // 将用户列表存储到 request 作用域中
        // 这样在 JSP 页面中可以通过 request.getAttribute("userList") 获取
        // 或使用 EL 表达式 ${userList} 访问
        request.setAttribute("userList", userList);
        
        // 使用 RequestDispatcher 将请求转发到用户管理页面
        // 转发是在服务器内部跳转，URL 不会改变，可以共享 request 中的数据
        request.getRequestDispatcher("/admin/userList.jsp").forward(request, response);
    }
    
    /**
     * POST 请求处理方法
     * 作用：处理 POST 方式的用户管理请求
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
