<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hx.entity.Users" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; margin: 0; padding: 20px; }
        .container { max-width: 600px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); }
        h1 { color: #333; margin-bottom: 30px; text-align: center; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input[type="text"], input[type="password"], input[type="email"] { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        input:focus { outline: none; border-color: #667eea; }
        .radio-group { display: flex; gap: 20px; padding: 12px 0; }
        .btn { padding: 12px 20px; border-radius: 5px; text-decoration: none; display: inline-block; cursor: pointer; border: none; font-size: 14px; margin-right: 10px; }
        .btn-primary { background: #667eea; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .message { padding: 10px; margin-bottom: 20px; background: #fee; color: #c33; border-radius: 5px; }
    </style>
</head>
<body>
    <% Users currentUser = (Users) session.getAttribute("currentUser");
       if (currentUser == null) {
           response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
       } %>
    
    <div class="container">
        <h1>个人信息</h1>
        <% String message = (String) request.getAttribute("message");
           if (message != null) { %>
            <div class="message"><%= message %></div>
        <% } %>
        
        <form action="<%= request.getContextPath() %>/updateUser" method="post" autocomplete="off">
            <input type="hidden" name="userId" value="<%= currentUser.getUserId() %>">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" name="userName" value="<%= currentUser.getUserName() %>" required autocomplete="new-password">
            </div>
            <div class="form-group">
                <label>密码（留空则不修改）</label>
                <input type="password" name="password" placeholder="请输入新密码" autocomplete="new-password">
            </div>
            <div class="form-group">
                <label>性别</label>
                <div class="radio-group">
                    <label><input type="radio" name="sex" value="男" <%= "男".equals(currentUser.getSex()) ? "checked" : "" %>> 男</label>
                    <label><input type="radio" name="sex" value="女" <%= "女".equals(currentUser.getSex()) ? "checked" : "" %>> 女</label>
                </div>
            </div>
            <div class="form-group">
                <label>邮箱</label>
                <input type="email" name="email" value="<%= currentUser.getEmail() %>" autocomplete="off">
            </div>
            <div style="margin-top: 30px;">
                <button type="submit" class="btn btn-primary">保存修改</button>
                <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-secondary">返回主页</a>
            </div>
        </form>
    </div>
</body>
</html>
