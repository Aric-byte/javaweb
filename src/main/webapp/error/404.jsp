<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>404 - 页面未找到</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; margin: 0; }
        .error-container { background: white; padding: 60px; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); text-align: center; max-width: 500px; }
        h1 { color: #f44336; font-size: 72px; margin: 0; }
        h2 { color: #333; margin: 20px 0; }
        p { color: #666; margin-bottom: 30px; }
        .btn { background: #667eea; color: white; padding: 12px 30px; border-radius: 5px; text-decoration: none; display: inline-block; }
        .btn:hover { background: #5568d3; }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>404</h1>
        <h2>页面未找到</h2>
        <p>抱歉，您访问的页面不存在或已被删除。</p>
        <a href="<%= request.getContextPath() %>/index.jsp" class="btn">返回首页</a>
    </div>
</body>
</html>
