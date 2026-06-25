<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hx.entity.Question" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>在线考试</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; margin: 0; padding: 20px; }
        .container { max-width: 800px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); }
        h1 { color: #333; margin-bottom: 10px; text-align: center; }
        .exam-info { text-align: center; color: #666; margin-bottom: 30px; padding-bottom: 20px; border-bottom: 2px solid #eee; }
        .question-item { margin-bottom: 30px; padding: 20px; background: #f8f9fa; border-radius: 10px; }
        .question-title { font-size: 16px; font-weight: bold; color: #333; margin-bottom: 15px; }
        .options { list-style: none; }
        .options li { margin-bottom: 10px; }
        .options label { display: block; padding: 10px 15px; background: white; border: 2px solid #ddd; border-radius: 5px; cursor: pointer; }
        .options label:hover { border-color: #667eea; background: #f0f4ff; }
        .options input[type="radio"] { margin-right: 10px; }
        .btn-submit { width: 100%; padding: 15px; background: #667eea; color: white; border: none; border-radius: 5px; font-size: 18px; cursor: pointer; margin-top: 20px; }
        .btn-submit:hover { background: #5568d3; }
    </style>
</head>
<body>
    <% List<Question> questions = (List<Question>) session.getAttribute("examQuestions");
       if (questions == null || questions.isEmpty()) {
           response.sendRedirect(request.getContextPath() + "/index.jsp"); return;
       } %>
    
    <div class="container">
        <h1>在线考试</h1>
        <div class="exam-info"><p>共 <%= questions.size() %> 道题，每题 25 分，总分 100 分</p></div>
        
        <form action="<%= request.getContextPath() %>/submitExam" method="post">
            <% for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i); %>
            <div class="question-item">
                <div class="question-title"><%= i + 1 %>. <%= q.getTitle() %></div>
                <ul class="options">
                    <li><label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="A"> A. <%= q.getOptionA() %></label></li>
                    <li><label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="B"> B. <%= q.getOptionB() %></label></li>
                    <li><label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="C"> C. <%= q.getOptionC() %></label></li>
                    <li><label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="D"> D. <%= q.getOptionD() %></label></li>
                </ul>
            </div>
            <% } %>
            <button type="submit" class="btn-submit">提交试卷</button>
        </form>
    </div>
</body>
</html>
