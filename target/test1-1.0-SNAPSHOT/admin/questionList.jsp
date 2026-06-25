<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hx.entity.Question" %>
<%@ page import="com.hx.entity.Users" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>试题管理</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; margin: 0; padding: 20px; }
        .container { max-width: 1200px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); }
        h1 { color: #333; margin-bottom: 30px; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #f8f9fa; color: #333; font-weight: bold; }
        tr:hover { background: #f8f9fa; }
        .btn { padding: 6px 15px; border-radius: 5px; text-decoration: none; display: inline-block; cursor: pointer; border: none; font-size: 12px; margin-right: 5px; }
        .btn-danger { background: #f44336; color: white; }
        .btn-primary { background: #667eea; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <% Users admin = (Users) session.getAttribute("currentUser");
       if (admin == null || !"admin".equals(admin.getRole())) {
           response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
       }
       List<Question> questionList = (List<Question>) request.getAttribute("questionList"); %>
    
    <div class="container">
        <h1>试题管理</h1>
        <p>欢迎，<%= admin.getUserName() %> | <a href="<%= request.getContextPath() %>/logout">退出登录</a></p>
        
        <div style="margin: 20px 0;">
            <a href="<%= request.getContextPath() %>/admin/addQuestion.jsp" class="btn btn-primary">添加试题</a>
            <a href="<%= request.getContextPath() %>/admin/userList" class="btn btn-secondary">用户管理</a>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th><th>题目</th><th>A</th><th>B</th><th>C</th><th>D</th><th>答案</th><th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% if (questionList != null) {
                    for (Question q : questionList) { %>
                <tr>
                    <td><%= q.getQuestionId() %></td>
                    <td><%= q.getTitle() %></td>
                    <td><%= q.getOptionA() %></td>
                    <td><%= q.getOptionB() %></td>
                    <td><%= q.getOptionC() %></td>
                    <td><%= q.getOptionD() %></td>
                    <td><strong><%= q.getAnswer() %></strong></td>
                    <td>
                        <form action="<%= request.getContextPath() %>/admin/deleteQuestion" method="post" style="display:inline;" onsubmit="return confirm('确定删除？');">
                            <input type="hidden" name="questionId" value="<%= q.getQuestionId() %>">
                            <button type="submit" class="btn btn-danger">删除</button>
                        </form>
                    </td>
                </tr>
                <% } } %>
            </tbody>
        </table>
    </div>
</body>
</html>
