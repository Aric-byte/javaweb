<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hx.entity.Users" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; margin: 0; padding: 20px; }
        .container { max-width: 1000px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); }
        h1 { color: #333; margin-bottom: 30px; }
        .navbar { margin-bottom: 30px; padding-bottom: 20px; border-bottom: 2px solid #eee; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #f8f9fa; color: #333; font-weight: bold; }
        tr:hover { background: #f8f9fa; }
        .btn { padding: 6px 15px; border-radius: 5px; text-decoration: none; display: inline-block; cursor: pointer; border: none; font-size: 12px; margin-right: 5px; }
        .btn-danger { background: #f44336; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <% Users admin = (Users) session.getAttribute("currentUser");
       if (admin == null || !"admin".equals(admin.getRole())) {
           response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
       }
       List<Users> userList = (List<Users>) request.getAttribute("userList"); %>
    
    <div class="container">
        <div class="navbar">
            <h1>用户管理</h1>
            <p>欢迎，<%= admin.getUserName() %> | <a href="<%= request.getContextPath() %>/logout">退出登录</a></p>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th><th>用户名</th><th>性别</th><th>邮箱</th><th>角色</th><th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% if (userList != null) {
                    for (Users u : userList) { %>
                <tr>
                    <td><%= u.getUserId() %></td>
                    <td><%= u.getUserName() %></td>
                    <td><%= u.getSex() %></td>
                    <td><%= u.getEmail() != null ? u.getEmail() : "-" %></td>
                    <td><%= "admin".equals(u.getRole()) ? "管理员" : "普通用户" %></td>
                    <td>
                        <% if (!"admin".equals(u.getRole())) { %>
                        <form action="<%= request.getContextPath() %>/admin/deleteUser" method="post" style="display:inline;" onsubmit="return confirm('确定删除？');">
                            <input type="hidden" name="userId" value="<%= u.getUserId() %>">
                            <button type="submit" class="btn btn-danger">删除</button>
                        </form>
                        <% } else { %>
                        -
                        <% } %>
                    </td>
                </tr>
                <% } } %>
            </tbody>
        </table>
        
        <div style="margin-top: 20px;">
            <a href="<%= request.getContextPath() %>/admin/questionList" class="btn btn-secondary">试题管理</a>
            <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-secondary">返回主页</a>
        </div>
    </div>
</body>
</html>
