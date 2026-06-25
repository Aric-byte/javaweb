<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; margin: 0; }
        .login-container { background: white; padding: 50px; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); width: 400px; }
        h1 { color: #333; margin-bottom: 30px; text-align: center; }
        .form-group { margin-bottom: 25px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input[type="text"], input[type="password"] { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        input:focus { outline: none; border-color: #667eea; }
        .btn-login { width: 100%; padding: 15px; background: #667eea; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; margin-top: 10px; }
        .btn-login:hover { background: #5568d3; }
        .message { padding: 10px; margin-bottom: 20px; background: #fee; color: #c33; border-radius: 5px; text-align: center; }
        .success-message { background: #efe; color: #3c3; }
        .links { margin-top: 20px; text-align: center; }
        .links a { color: #667eea; text-decoration: none; margin: 0 10px; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>用户登录</h1>
        
        <% 
            String message = (String) request.getAttribute("message");
            String msg = request.getParameter("msg");
            if (message != null) { 
        %>
            <div class="message"><%= message %></div>
        <% 
            } else if ("register_success".equals(msg)) {
        %>
            <div class="message success-message">注册成功，请登录！</div>
        <% } %>
        
        <form action="<%= request.getContextPath() %>/login" method="post" autocomplete="off">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" name="userName" required placeholder="请输入用户名" autocomplete="new-password">
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" required placeholder="请输入密码" autocomplete="new-password">
            </div>
            <button type="submit" class="btn-login">登录</button>
        </form>
        
        <div class="links">
            <a href="<%= request.getContextPath() %>/register.jsp">还没有账号？立即注册</a>
            <a href="<%= request.getContextPath() %>/index.jsp">返回首页</a>
        </div>
        
        <div style="margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; text-align: center; color: #999; font-size: 12px;">
            <p>测试账号：</p>
            <p>管理员：admin / admin123</p>
            <p>普通用户：student1 / 123456</p>
        </div>
    </div>
</body>
</html>
