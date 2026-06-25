<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hx.entity.Users" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>在线考试系统 - 首页</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; margin: 0; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; }
        .header { background: white; padding: 20px 30px; border-radius: 10px; box-shadow: 0 5px 20px rgba(0,0,0,0.2); margin-bottom: 30px; display: flex; justify-content: space-between; align-items: center; }
        .logo { font-size: 28px; font-weight: bold; color: #667eea; }
        .nav a { margin-left: 20px; text-decoration: none; color: #333; padding: 8px 15px; border-radius: 5px; }
        .nav a:hover { background: #f0f4ff; }
        .btn-primary { background: #667eea; color: white; padding: 10px 25px; border-radius: 5px; text-decoration: none; display: inline-block; }
        .btn-primary:hover { background: #5568d3; }
        .content { background: white; padding: 40px; border-radius: 10px; box-shadow: 0 5px 20px rgba(0,0,0,0.2); text-align: center; }
        .welcome { font-size: 36px; color: #333; margin-bottom: 20px; }
        .description { color: #666; font-size: 18px; margin-bottom: 40px; line-height: 1.8; }
        .features { display: flex; gap: 30px; justify-content: center; margin-top: 40px; }
        .feature-item { flex: 1; max-width: 300px; padding: 30px; background: #f8f9fa; border-radius: 10px; }
        .feature-title { font-size: 20px; color: #667eea; margin-bottom: 15px; }
        .feature-desc { color: #666; line-height: 1.6; }
        .visit-count { margin-top: 30px; padding: 20px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 10px; color: white; display: inline-block; }
        .visit-count-number { font-size: 48px; font-weight: bold; margin: 10px 0; }
        .visit-count-label { font-size: 16px; opacity: 0.9; }
    </style>
</head>
<body>
    <% Users currentUser = (Users) session.getAttribute("currentUser"); %>
    
    <div class="container">
        <div class="header">
            <div class="logo">📚 在线考试系统</div>
            <div class="nav">
                <% if (currentUser != null) { %>
                    <span style="color: #666; margin-right: 15px;">欢迎，<%= currentUser.getUserName() %></span>
                    <a href="<%= request.getContextPath() %>/profile.jsp">个人信息</a>
                    <% if ("admin".equals(currentUser.getRole())) { %>
                        <a href="<%= request.getContextPath() %>/admin/questionList">试题管理</a>
                        <a href="<%= request.getContextPath() %>/admin/userList">用户管理</a>
                    <% } %>
                    <a href="<%= request.getContextPath() %>/logout">退出登录</a>
                <% } else { %>
                    <a href="<%= request.getContextPath() %>/login.jsp">登录</a>
                    <a href="<%= request.getContextPath() %>/register.jsp">注册</a>
                <% } %>
            </div>
        </div>
        
        <div class="content">
            <div class="welcome">欢迎来到在线考试系统</div>
            <div class="description">
                <p>本系统提供便捷的在线考试功能，支持自动随机出题、在线答题、自动阅卷评分</p>
                <p>登录后即可开始考试或管理试题</p>
            </div>
            
            <% if (currentUser != null) { %>
                <a href="<%= request.getContextPath() %>/startExam" class="btn-primary" style="font-size: 20px; padding: 15px 50px;">🚀 开始考试</a>
                
                <!-- 显示网站访问人数 -->
                <div class="visit-count">
                    <div class="visit-count-label">📊 网站访问人数统计</div>
                    <div class="visit-count-number"><%= application.getAttribute("visitCount") != null ? application.getAttribute("visitCount") : 0 %></div>
                    <div class="visit-count-label">人次访问</div>
                </div>
            <% } else { %>
                <a href="<%= request.getContextPath() %>/login.jsp" class="btn-primary" style="font-size: 20px; padding: 15px 50px;">🚀 立即体验</a>
            <% } %>
            
            <div class="features">
                <div class="feature-item">
                    <div class="feature-title">📝 随机出题</div>
                    <div class="feature-desc">系统从题库中随机抽取试题，每次考试都是全新的挑战</div>
                </div>
                <div class="feature-item">
                    <div class="feature-title">⚡ 在线答题</div>
                    <div class="feature-desc">简洁友好的答题界面，轻松完成考试</div>
                </div>
                <div class="feature-item">
                    <div class="feature-title">🎯 自动阅卷</div>
                    <div class="feature-desc">交卷后系统立即评分，实时查看成绩</div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
