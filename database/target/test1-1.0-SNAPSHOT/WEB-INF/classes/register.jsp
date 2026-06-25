<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; margin: 0; }
        .register-container { background: white; padding: 50px; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); width: 450px; }
        h1 { color: #333; margin-bottom: 30px; text-align: center; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input[type="text"], input[type="password"], input[type="email"] { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        input:focus { outline: none; border-color: #667eea; }
        .radio-group { display: flex; gap: 20px; padding: 10px 0; }
        .btn-register { width: 100%; padding: 15px; background: #667eea; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; margin-top: 10px; }
        .btn-register:hover { background: #5568d3; }
        .message { padding: 10px; margin-bottom: 20px; background: #fee; color: #c33; border-radius: 5px; text-align: center; }
        .links { margin-top: 20px; text-align: center; }
        .links a { color: #667eea; text-decoration: none; margin: 0 10px; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="register-container">
        <h1>用户注册</h1>
        
        <% String message = (String) request.getAttribute("message");
           if (message != null) { %>
            <div class="message"><%= message %></div>
        <% } %>
        
        <form action="<%= request.getContextPath() %>/register" method="post" autocomplete="off">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" name="userName" required placeholder="请输入用户名" autocomplete="new-password">
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" required placeholder="请输入密码" autocomplete="new-password">
            </div>
            <div class="form-group">
                <label>性别</label>
                <div class="radio-group">
                    <label><input type="radio" name="sex" value="男" checked> 男</label>
                    <label><input type="radio" name="sex" value="女"> 女</label>
                </div>
            </div>
            <div class="form-group">
                <label>邮箱</label>
                <input type="email" name="email" placeholder="请输入邮箱（选填）" autocomplete="off">
            </div>
            <button type="submit" class="btn-register">注册</button>
        </form>
        
        <div class="links">
            <span>已有账号？</span>
            <a href="<%= request.getContextPath() %>/login.jsp">立即登录</a>
            <a href="<%= request.getContextPath() %>/index.jsp">返回首页</a>
        </div>
    </div>
</body>
</html>
