package com.hx.test;

import com.hx.util.JdbcUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 性能对比测试 - 传统 JDBC vs 连接池
 * 
 * 作用：
 * 1. 测试传统方式访问数据库100次的时间
 * 2. 测试连接池方式访问数据库100次的时间
 * 3. 对比两种方式性能差异
 * 
 * @author hx
 * @version 1.0
 */
public class PerformanceTest {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/exam_system?useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "aric2006";
    private static final String TEST_SQL = "SELECT * FROM Users WHERE userId = ?";
    private static final int TEST_COUNT = 100;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   数据库访问性能对比测试");
        System.out.println("   测试次数: " + TEST_COUNT + " 次");
        System.out.println("========================================\n");
        
        // 测试传统 JDBC 方式
        long traditionalTime = testTraditionalJDBC();
        
        // 测试连接池方式
        long poolTime = testConnectionPool();
        
        // 输出对比结果
        System.out.println("\n========================================");
        System.out.println("   测试结果对比");
        System.out.println("========================================");
        System.out.println("传统 JDBC 耗时: " + traditionalTime + " ms");
        System.out.println("连接池方式耗时: " + poolTime + " ms");
        System.out.println("性能提升: " + String.format("%.2f", (traditionalTime * 1.0 / poolTime)) + " 倍");
        System.out.println("节省时间: " + (traditionalTime - poolTime) + " ms");
        System.out.println("========================================");
        
        // 打印连接池状态
        System.out.println("\n连接池状态:");
        System.out.println("活跃连接数: " + JdbcUtil.getActiveConnections());
        System.out.println("空闲连接数: " + JdbcUtil.getIdleConnections());
        System.out.println("总连接数: " + JdbcUtil.getTotalConnections());
    }
    
    /**
     * 测试传统 JDBC 方式（每次都创建新连接）
     * 
     * @return 耗时（毫秒）
     */
    private static long testTraditionalJDBC() {
        System.out.println("【测试1】传统 JDBC 方式（每次创建新连接）");
        System.out.println("开始测试...");
        
        long startTime = System.currentTimeMillis();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            for (int i = 1; i <= TEST_COUNT; i++) {
                // 每次循环都创建新连接（模拟传统方式）
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                pstmt = conn.prepareStatement(TEST_SQL);
                pstmt.setInt(1, (i % 10) + 1); // 随机查询不同用户
                rs = pstmt.executeQuery();
                
                // 消耗结果集
                while (rs.next()) {
                    // 不做实际操作
                }
                
                // 立即关闭连接
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
                
                // 重置引用
                rs = null;
                pstmt = null;
                conn = null;
                
                // 每10次输出进度
                if (i % 10 == 0) {
                    System.out.println("  已完成: " + i + "/" + TEST_COUNT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 确保资源关闭
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("传统 JDBC 测试完成，耗时: " + duration + " ms\n");
        return duration;
    }
    
    /**
     * 测试连接池方式（复用连接）
     * 
     * @return 耗时（毫秒）
     */
    private static long testConnectionPool() {
        System.out.println("【测试2】HikariCP 连接池方式（复用连接）");
        System.out.println("开始测试...");
        
        long startTime = System.currentTimeMillis();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            for (int i = 1; i <= TEST_COUNT; i++) {
                // 从连接池获取连接（复用已有连接）
                conn = JdbcUtil.getConnection();
                pstmt = conn.prepareStatement(TEST_SQL);
                pstmt.setInt(1, (i % 10) + 1); // 随机查询不同用户
                rs = pstmt.executeQuery();
                
                // 消耗结果集
                while (rs.next()) {
                    // 不做实际操作
                }
                
                // 关闭资源（实际是归还到连接池）
                JdbcUtil.closeAll(rs, pstmt, conn);
                
                // 重置引用
                rs = null;
                pstmt = null;
                conn = null;
                
                // 每10次输出进度
                if (i % 10 == 0) {
                    System.out.println("  已完成: " + i + "/" + TEST_COUNT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 确保资源关闭
            JdbcUtil.closeAll(rs, pstmt, conn);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("连接池测试完成，耗时: " + duration + " ms\n");
        return duration;
    }
}
