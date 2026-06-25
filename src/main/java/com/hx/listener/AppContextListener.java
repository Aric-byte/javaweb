package com.hx.listener;

import com.hx.util.JdbcUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * 应用上下文监听器 - 用于初始化全局数据和资源清理
 * 
 * 作用：
 * 1. 在Web应用启动时初始化访问人数计数器
 * 2. 将计数器存储到ServletContext中，实现全局共享
 * 3. 确保服务器重启后访问人数从0开始计数
 * 4. 在应用关闭时优雅地关闭数据库连接池
 * 
 * @author hx
 * @version 2.0
 */
@WebListener
public class AppContextListener implements ServletContextListener {
    
    /**
     * 上下文初始化方法 - Web应用启动时自动调用
     * 
     * 处理流程：
     * 1. 获取ServletContext对象
     * 2. 初始化访问人数为0
     * 3. 将访问人数存储到ServletContext中
     * 
     * @param sce ServletContextEvent 事件对象，包含ServletContext引用
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 获取ServletContext对象，用于存储全局数据
        ServletContext context = sce.getServletContext();
        
        // 初始化访问人数为0
        Integer visitCount = 0;
        
        // 将访问人数存储到ServletContext中
        // ServletContext中的数据在整个应用生命周期内共享
        context.setAttribute("visitCount", visitCount);
        
        // 打印初始化信息到控制台
        System.out.println("=== 在线考试系统启动成功 ===");
        System.out.println("访问人数计数器已初始化: " + visitCount);
    }
    
    /**
     * 上下文销毁方法 - Web应用停止时自动调用
     * 
     * @param sce ServletContextEvent 事件对象
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 获取最终访问人数
        ServletContext context = sce.getServletContext();
        Integer finalCount = (Integer) context.getAttribute("visitCount");
        
        // 打印最终访问人数
        System.out.println("=== 在线考试系统关闭 ===");
        System.out.println("总访问人数: " + finalCount);
        
        // 优雅关闭数据库连接池
        JdbcUtil.shutdown();
    }
}
