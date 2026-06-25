<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>添加试题</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; margin: 0; padding: 20px; }
        .container { max-width: 600px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); }
        h1 { color: #333; margin-bottom: 30px; text-align: center; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input[type="text"], textarea { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        input:focus, textarea:focus { outline: none; border-color: #667eea; }
        .btn { padding: 12px 20px; border-radius: 5px; text-decoration: none; display: inline-block; cursor: pointer; border: none; font-size: 14px; margin-right: 10px; }
        .btn-primary { background: #667eea; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .btn:hover { opacity: 0.9; }
        .message { padding: 10px; margin-bottom: 20px; background: #fee; color: #c33; border-radius: 5px; }
    </style>
</head>
<body>
    <% String message = (String) request.getAttribute("message"); %>
    
    <div class="container">
        <h1>添加试题</h1>
        <% if (message != null) { %><div class="message"><%= message %></div><% } %>
        
        <form action="<%= request.getContextPath() %>/admin/addQuestion" method="post">
            <div class="form-group">
                <label>题目内容</label>
                <textarea name="title" rows="3" required></textarea>
            </div>
            <div class="form-group">
                <label>选项 A</label>
                <input type="text" name="optionA" required>
            </div>
            <div class="form-group">
                <label>选项 B</label>
                <input type="text" name="optionB" required>
            </div>
            <div class="form-group">
                <label>选项 C</label>
                <input type="text" name="optionC" required>
            </div>
            <div class="form-group">
                <label>选项 D</label>
                <input type="text" name="optionD" required>
            </div>
            <div class="form-group">
                <label>正确答案</label>
                <select name="answer" style="width:100%;padding:12px;border:1px solid #ddd;border-radius:5px;" required>
                    <option value="A">A</option>
                    <option value="B">B</option>
                    <option value="C">C</option>
                    <option value="D">D</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">添加试题</button>
            <a href="<%= request.getContextPath() %>/admin/questionList" class="btn btn-secondary">返回列表</a>
        </form>
    </div>
</body>
</html>
