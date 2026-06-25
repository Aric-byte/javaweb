<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>考试成绩</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; margin: 0; }
        .score-container { background: white; padding: 60px; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); text-align: center; max-width: 500px; }
        h1 { color: #333; margin-bottom: 30px; font-size: 32px; }
        .score-display { width: 200px; height: 200px; border-radius: 50%; background: #667eea; color: white; display: flex; justify-content: center; align-items: center; margin: 0 auto 30px; font-size: 48px; font-weight: bold; }
        .score-info { color: #666; margin-bottom: 30px; line-height: 1.8; }
        .btn-group { display: flex; gap: 15px; justify-content: center; }
        .btn { padding: 12px 30px; border-radius: 5px; text-decoration: none; display: inline-block; cursor: pointer; border: none; font-size: 14px; }
        .btn-primary { background: #667eea; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <% Integer score = (Integer) request.getAttribute("score");
       Integer totalQuestions = (Integer) request.getAttribute("totalQuestions");
       if (score == null) {
           response.sendRedirect(request.getContextPath() + "/index.jsp"); return;
       }
       String level = score >= 90 ? "优秀" : score >= 80 ? "良好" : score >= 60 ? "及格" : "不及格";
       String msg = score >= 90 ? "太棒了！" : score >= 80 ? "不错哦！" : score >= 60 ? "加油！" : "不要气馁！"; %>
    
    <div class="score-container">
        <h1>考试成绩</h1>
        <div class="score-display"><%= score %></div>
        <div class="score-info">
            <p><strong>得分：</strong><%= score %> 分</p>
            <p><strong>题目数量：</strong><%= totalQuestions %> 道</p>
            <p><strong>成绩等级：</strong><%= level %></p>
            <p style="margin-top: 15px; color: #667eea; font-weight: bold;"><%= msg %></p>
        </div>
        <div class="btn-group">
            <a href="<%= request.getContextPath() %>/startExam" class="btn btn-primary">再次考试</a>
            <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-secondary">返回主页</a>
        </div>
    </div>
</body>
</html>
