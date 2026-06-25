package com.hx.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录安全过滤器 - 防止恶意登录和暴力破解
 * 
 * 作用：
 * 1. 限制同一IP地址的登录尝试次数
 * 2. 当尝试次数超过阈值时，临时锁定该IP
 * 3. 记录登录失败次数，增强系统安全性
 * 
 * @author hx
 * @version 1.0
 */
@WebFilter("/login")
public class LoginSecurityFilter implements Filter {
    
    // 存储每个IP的登录尝试次数
    private static final Map<String, Integer> loginAttempts = new ConcurrentHashMap<>();
    
    // 存储每个IP的锁定状态
    private static final Map<String, Long> lockedIPs = new ConcurrentHashMap<>();
    
    // 最大登录尝试次数
    private static final int MAX_ATTEMPTS = 5;
    
    // 锁定时长（毫秒）- 5分钟
    private static final long LOCK_TIME = 5 * 60 * 1000;
    
    /**
     * 过滤器初始化方法
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("登录安全过滤器已启动");
    }
    
    /**
     * 过滤处理方法
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // 获取客户端IP地址
        String clientIP = getClientIP(httpRequest);
        
        // 检查IP是否被锁定
        if (isIPLocked(clientIP)) {
            // IP被锁定，返回错误信息
            httpRequest.setAttribute("message", "登录尝试次数过多，账户已被锁定5分钟，请稍后再试！");
            httpRequest.getRequestDispatcher("/login.jsp").forward(httpRequest, httpResponse);
            return;
        }
        
        // 继续处理请求
        chain.doFilter(request, response);
        
        // 在请求处理后检查是否需要记录失败尝试
        // 这里需要在LoginServlet中配合使用
    }
    
    /**
     * 检查IP是否被锁定
     */
    private boolean isIPLocked(String ip) {
        Long lockTime = lockedIPs.get(ip);
        if (lockTime == null) {
            return false;
        }
        
        // 检查锁定时间是否已过期
        if (System.currentTimeMillis() - lockTime > LOCK_TIME) {
            // 锁定已过期，移除锁定状态和尝试次数
            lockedIPs.remove(ip);
            loginAttempts.remove(ip);
            return false;
        }
        
        return true;
    }
    
    /**
     * 记录登录失败
     */
    public static void recordFailedLogin(String ip) {
        Integer attempts = loginAttempts.getOrDefault(ip, 0) + 1;
        loginAttempts.put(ip, attempts);
        
        System.out.println("IP: " + ip + " 登录失败次数: " + attempts);
        
        // 如果尝试次数超过阈值，锁定IP
        if (attempts >= MAX_ATTEMPTS) {
            lockedIPs.put(ip, System.currentTimeMillis());
            System.out.println("IP: " + ip + " 已被锁定");
        }
    }
    
    /**
     * 清除登录尝试记录（登录成功后调用）
     */
    public static void clearLoginAttempts(String ip) {
        loginAttempts.remove(ip);
        lockedIPs.remove(ip);
    }
    
    /**
     * 获取客户端真实IP地址
     */
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于多个代理的情况，第一个IP为客户端真实IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
    
    /**
     * 过滤器销毁方法
     */
    @Override
    public void destroy() {
        System.out.println("登录安全过滤器已停止");
    }
}