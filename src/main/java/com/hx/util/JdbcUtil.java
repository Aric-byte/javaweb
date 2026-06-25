package com.hx.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库工具类 - 提供数据库连接和资源管理的通用方法
 * 
 * 作用：
 * 1. 封装数据库连接配置信息
 * 2. 使用 HikariCP 连接池管理数据库连接，提升性能
 * 3. 提供统一的资源关闭方法，防止资源泄露
 * 
 * 实现方法：
 * 1. 从 db.properties 文件加载数据库配置和连接池参数
 * 2. 在静态代码块中初始化 HikariCP 连接池
 * 3. 通过连接池获取数据库连接（复用连接，减少开销）
 * 4. 提供重载的 closeAll 方法，安全关闭 ResultSet、PreparedStatement 和 Connection
 * 
 * @author hx
 * @version 2.0
 */
public class JdbcUtil {
    
    // HikariCP 数据源（连接池核心对象）
    private static final HikariDataSource DATA_SOURCE;
    
    /**
     * 静态代码块 - 在类首次加载时执行
     * 作用：加载数据库配置文件并初始化 HikariCP 连接池
     * 只执行一次，确保连接池不会被重复创建
     */
    static {
        Properties props = new Properties();
        InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
        
        if (is == null) {
            throw new RuntimeException("找不到 db.properties 配置文件，请确保文件位于 src/main/resources 目录下");
        }
        
        try {
            // 加载配置文件
            props.load(is);
            
            // 创建 HikariCP 配置对象
            HikariConfig config = new HikariConfig();
            
            // 设置数据库连接参数
            config.setDriverClassName(props.getProperty("jdbc.driver"));
            config.setJdbcUrl(props.getProperty("jdbc.url"));
            config.setUsername(props.getProperty("jdbc.username"));
            config.setPassword(props.getProperty("jdbc.password"));
            
            // 设置连接池参数
            config.setMinimumIdle(Integer.parseInt(props.getProperty("hikari.minimumIdle", "5")));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("hikari.maximumPoolSize", "20")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("hikari.connectionTimeout", "30000")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("hikari.idleTimeout", "600000")));
            config.setMaxLifetime(Long.parseLong(props.getProperty("hikari.maxLifetime", "1800000")));
            config.setConnectionTestQuery(props.getProperty("hikari.connectionTestQuery", "SELECT 1"));
            config.setPoolName(props.getProperty("hikari.poolName", "ExamSystemPool"));
            
            // 创建数据源（连接池）
            DATA_SOURCE = new HikariDataSource(config);
            
            System.out.println("=== HikariCP 连接池初始化成功 ===");
            System.out.println("最小空闲连接: " + config.getMinimumIdle());
            System.out.println("最大连接数: " + config.getMaximumPoolSize());
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("初始化数据库连接池失败", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 获取数据库连接
     * 作用：从连接池中获取一个可用连接（如果池中没有空闲连接则创建新连接）
     * 
     * @return Connection 数据库连接对象
     * @throws SQLException 当连接失败时抛出 SQL 异常
     */
    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
    
    /**
     * 获取连接池中的活跃连接数（用于监控）
     * 
     * @return int 当前正在使用的连接数
     */
    public static int getActiveConnections() {
        return DATA_SOURCE.getHikariPoolMXBean().getActiveConnections();
    }
    
    /**
     * 获取连接池中的空闲连接数（用于监控）
     * 
     * @return int 当前空闲的连接数
     */
    public static int getIdleConnections() {
        return DATA_SOURCE.getHikariPoolMXBean().getIdleConnections();
    }
    
    /**
     * 获取连接池总连接数（用于监控）
     * 
     * @return int 连接池中的总连接数
     */
    public static int getTotalConnections() {
        return DATA_SOURCE.getHikariPoolMXBean().getTotalConnections();
    }
    
    /**
     * 关闭资源（完整版）
     * 作用：按正确顺序关闭数据库资源，防止资源泄露
     * 关闭顺序：ResultSet -> PreparedStatement -> Connection
     * 
     * 注意：这里的 conn.close() 实际上是将连接归还到连接池，而不是真正关闭物理连接
     * 
     * @param rs ResultSet 结果集对象
     * @param ps PreparedStatement 预编译语句对象
     * @param conn Connection 数据库连接对象
     */
    public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {
        // 依次关闭各个资源，每个资源的关闭都进行异常捕获，避免一个资源关闭失败影响其他资源
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close(); // 归还连接到连接池
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 关闭资源（简化版，无结果集）
     * 作用：当 SQL 操作不返回结果集时（如 INSERT、UPDATE、DELETE），使用此方法关闭资源
     * 实现：调用完整版的 closeAll 方法，传入 null 作为 ResultSet 参数
     * 
     * @param ps PreparedStatement 预编译语句对象
     * @param conn Connection 数据库连接对象
     */
    public static void closeAll(PreparedStatement ps, Connection conn) {
        closeAll(null, ps, conn);
    }
    
    /**
     * 关闭连接池（应用关闭时调用）
     * 作用：关闭连接池，释放所有资源
     */
    public static void shutdown() {
        if (DATA_SOURCE != null && !DATA_SOURCE.isClosed()) {
            DATA_SOURCE.close();
            System.out.println("=== HikariCP 连接池已关闭 ===");
        }
    }
}
