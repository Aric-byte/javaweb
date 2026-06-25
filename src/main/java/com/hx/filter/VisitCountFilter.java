package com.hx.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 访问统计过滤器 - 统计网站访问人数
 * 
 * 作用：
 * 1. 拦截所有用户请求
 * 2. 每次有新访问时，访问人数计数器加1
 * 3. 使用ServletContext实现全局共享的计数器
 * 4. 区分新用户访问和页面刷新（基于Session）
 * 
 * @author hx
 * @version 1.0
 */
@WebFilter("/*")
public class VisitCountFilter implements Filter {
    
    /**
     * 过滤器初始化方法
     * 
     * @param filterConfig 过滤器配置对象
     * @throws ServletException Servlet异常
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("访问统计过滤器已启动");
    }
    
    /**
     * 过滤处理方法 - 每次请求都会执行
     * 
     * 处理流程：
     * 1. 获取用户的Session（如果不存在则创建）
     * 2. 检查是否是新会话（首次访问）
     * 3. 如果是新会话，访问人数计数器加1
     * 4. 标记该会话已统计过
     * 5. 继续处理请求链
     * 
     * @param request ServletRequest 请求对象
     * @param response ServletResponse 响应对象
     * @param chain FilterChain 过滤器链
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // 将ServletRequest转换为HttpServletRequest，以使用Session相关方法
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // 获取当前请求的Session对象
        // true表示如果Session不存在则创建一个新的
        HttpSession session = httpRequest.getSession(true);
        
        // 检查是否是一个新的会话（用户首次访问）
        if (session.isNew()) {
            // 获取ServletContext对象
            ServletContext context = request.getServletContext();
            
            // 从ServletContext中获取当前的访问人数
            Integer visitCount = (Integer) context.getAttribute("visitCount");
            
            // 如果访问人数为null，初始化为0
            if (visitCount == null) {
                visitCount = 0;
            }
            
            // 访问人数加1
            visitCount++;
            
            // 将更新后的访问人数存回ServletContext
            context.setAttribute("visitCount", visitCount);
            
            // 打印访问信息到控制台
            System.out.println("新用户访问，当前访问人数: " + visitCount);
        }
        
        // 继续执行过滤器链中的下一个过滤器或目标资源
        chain.doFilter(request, response);
    }
    
    /**
     * 过滤器销毁方法
     */
    @Override
    public void destroy() {
        System.out.println("访问统计过滤器已停止");
    }
}
