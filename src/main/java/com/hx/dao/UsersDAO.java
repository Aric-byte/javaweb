package com.hx.dao;

import com.hx.entity.Users;
import com.hx.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据访问层（DAO） - 负责对用户数据进行数据库操作
 * 
 * 作用：
 * 1. 封装所有与 Users 表相关的数据库操作
 * 2. 提供用户登录、注册、查询、更新、删除等功能
 * 3. 将数据库操作细节与业务逻辑分离，提高代码可维护性
 * 
 * 实现方法：
 * 1. 使用 JdbcUtil 获取和关闭数据库连接
 * 2. 使用 PreparedStatement 执行参数化 SQL 语句，防止 SQL 注入
 * 3. 使用 ResultSet 处理查询结果
 * 4. 将数据库记录映射为 Users 实体对象
 * 
 * @author hx
 * @version 1.0
 */
public class UsersDAO {
    
    /**
     * 用户登录验证
     * 
     * 作用：验证用户名和密码，完成用户登录
     * 实现方法：
     * 1. 编写 SQL 查询语句，使用 WHERE 条件匹配用户名和密码
     * 2. 使用 PreparedStatement 设置参数，防止 SQL 注入
     * 3. 执行查询，如果找到匹配记录则创建 Users 对象
     * 4. 返回用户对象（登录成功）或 null（登录失败）
     * 
     * @param userName 用户名，从登录表单获取
     * @param password 密码，从登录表单获取
     * @return Users 用户对象，包含完整的用户信息；登录失败返回 null
     */
    public Users login(String userName, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Users user = null;
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 查询语句：根据用户名和密码查询用户
            String sql = "SELECT * FROM Users WHERE userName = ? AND password = ?";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 设置参数：第 1 个参数为用户名
            ps.setString(1, userName);
            // 设置参数：第 2 个参数为密码
            ps.setString(2, password);
            // 执行查询，返回结果集
            rs = ps.executeQuery();
            
            // 如果结果集有数据，说明登录成功
            if (rs.next()) {
                // 创建用户对象
                user = new Users();
                // 从结果集中获取各字段值并设置到用户对象中
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setSex(rs.getString("sex"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            // 捕获并打印 SQL 异常
            e.printStackTrace();
        } finally {
            // 在 finally 块中关闭资源，确保资源被释放
            JdbcUtil.closeAll(rs, ps, conn);
        }
        
        return user;
    }
    
    /**
     * 用户注册
     * 
     * 作用：将新用户信息插入到数据库中
     * 实现方法：
     * 1. 编写 INSERT SQL 语句
     * 2. 使用 PreparedStatement 设置用户信息的各个参数
     * 3. 执行插入操作，返回受影响的行数
     * 4. 根据返回值判断是否注册成功
     * 
     * @param user 用户对象，包含待注册的用户信息
     * @return boolean 注册成功返回 true，失败返回 false
     */
    public boolean register(Users user) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 插入语句：向 Users 表插入新记录，userId 为自增主键，无需手动设置
            String sql = "INSERT INTO Users (userName, password, sex, email, role) VALUES (?, ?, ?, ?, ?)";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 按顺序设置参数
            ps.setString(1, user.getUserName());  // 用户名
            ps.setString(2, user.getPassword());  // 密码
            ps.setString(3, user.getSex());       // 性别
            ps.setString(4, user.getEmail());     // 邮箱
            ps.setString(5, user.getRole());      // 角色
            
            // 执行插入操作，rows 为受影响的行数
            int rows = ps.executeUpdate();
            // 如果受影响行数大于 0，说明插入成功
            return rows > 0;
        } catch (SQLException e) {
            // 捕获异常并打印
            e.printStackTrace();
            return false;
        } finally {
            // 关闭资源
            JdbcUtil.closeAll(ps, conn);
        }
    }
    
    /**
     * 根据 ID 查询用户
     * 
     * 作用：通过用户 ID 查询用户的详细信息
     * 实现方法：
     * 1. 编写 SELECT SQL 语句，使用 WHERE 条件匹配 userId
     * 2. 使用 PreparedStatement 设置 userId 参数
     * 3. 执行查询，从结果集中获取用户信息
     * 4. 创建并返回 Users 对象
     * 
     * @param userId 用户 ID，主键
     * @return Users 用户对象；如果未找到用户返回 null
     */
    public Users findById(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Users user = null;
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 查询语句：根据用户 ID 查询
            String sql = "SELECT * FROM Users WHERE userId = ?";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 设置参数：用户 ID
            ps.setInt(1, userId);
            // 执行查询
            rs = ps.executeQuery();
            
            // 如果结果集有数据
            if (rs.next()) {
                // 创建用户对象
                user = new Users();
                // 从结果集中获取各字段值
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setSex(rs.getString("sex"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, ps, conn);
        }
        
        return user;
    }
    
    /**
     * 查询所有用户
     * 
     * 作用：获取数据库中所有用户的列表
     * 实现方法：
     * 1. 编写 SELECT ALL SQL 语句，按 userId 排序
     * 2. 执行查询，遍历结果集
     * 3. 对每条记录创建 Users 对象并添加到列表中
     * 4. 返回用户列表
     * 
     * @return List<Users> 包含所有用户的 ArrayList
     */
    public List<Users> findAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // 创建 ArrayList 存储用户列表
        List<Users> userList = new ArrayList<>();
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 查询语句：查询所有用户，按 userId 升序排列
            String sql = "SELECT * FROM Users ORDER BY userId";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 执行查询
            rs = ps.executeQuery();
            
            // 遍历结果集
            while (rs.next()) {
                // 为每条记录创建用户对象
                Users user = new Users();
                // 从结果集中获取各字段值
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setSex(rs.getString("sex"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                // 将用户对象添加到列表中
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            JdbcUtil.closeAll(rs, ps, conn);
        }
        
        return userList;
    }
    
    /**
     * 更新用户信息
     * 
     * 作用：修改数据库中指定用户的信息
     * 实现方法：
     * 1. 编写 UPDATE SQL 语句，使用 WHERE 条件匹配 userId
     * 2. 使用 PreparedStatement 设置新的用户信息参数
     * 3. 执行更新操作，返回受影响的行数
     * 4. 根据返回值判断是否更新成功
     * 
     * @param user 用户对象，包含更新后的用户信息（userId 用于定位记录）
     * @return boolean 更新成功返回 true，失败返回 false
     */
    public boolean update(Users user) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 更新语句：根据 userId 更新用户信息
            String sql = "UPDATE Users SET userName = ?, password = ?, sex = ?, email = ?, role = ? WHERE userId = ?";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 按顺序设置参数
            ps.setString(1, user.getUserName());   // 新用户名
            ps.setString(2, user.getPassword());   // 新密码
            ps.setString(3, user.getSex());        // 新性别
            ps.setString(4, user.getEmail());      // 新邮箱
            ps.setString(5, user.getRole());       // 新角色
            ps.setInt(6, user.getUserId());        // 用户 ID（WHERE 条件）
            
            // 执行更新操作，rows 为受影响的行数
            int rows = ps.executeUpdate();
            // 如果受影响行数大于 0，说明更新成功
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭资源
            JdbcUtil.closeAll(ps, conn);
        }
    }
    
    /**
     * 删除用户
     * 
     * 作用：从数据库中删除指定用户
     * 实现方法：
     * 1. 编写 DELETE SQL 语句，使用 WHERE 条件匹配 userId
     * 2. 使用 PreparedStatement 设置 userId 参数
     * 3. 执行删除操作，返回受影响的行数
     * 4. 根据返回值判断是否删除成功
     * 
     * @param userId 要删除的用户 ID
     * @return boolean 删除成功返回 true，失败返回 false
     */
    public boolean delete(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 删除语句：根据用户 ID 删除记录
            String sql = "DELETE FROM Users WHERE userId = ?";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 设置参数：用户 ID
            ps.setInt(1, userId);
            
            // 执行删除操作，rows 为受影响的行数
            int rows = ps.executeUpdate();
            // 如果受影响行数大于 0，说明删除成功
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭资源
            JdbcUtil.closeAll(ps, conn);
        }
    }
}
