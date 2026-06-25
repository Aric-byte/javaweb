package com.hx.entity;

import java.io.Serializable;

/**
 * 用户实体类 - 用于在程序中表示用户信息
 * 
 * 作用：封装用户相关的数据，包括用户 ID、用户名、密码、性别、邮箱和角色
 * 实现方法：
 * 1. 实现 Serializable 接口，支持对象序列化，便于在网络传输和会话存储中使用
 * 2. 提供私有属性存储用户数据
 * 3. 提供无参构造方法和全参构造方法
 * 4. 提供 getter/setter 方法用于访问和修改属性
 * 5. 重写 toString() 方法方便调试和日志输出
 * 
 * @author hx
 * @version 1.0
 */
public class Users implements Serializable {
    // 用户唯一标识符，对应数据库中的主键
    private Integer userId;
    // 用户名，用于登录和显示
    private String userName;
    // 用户密码，明文存储（生产环境建议加密）
    private String password;
    // 用户性别
    private String sex;
    // 用户邮箱地址
    private String email;
    // 用户角色：admin（管理员）或 user（普通用户），用于权限控制
    private String role;

    /**
     * 无参构造方法
     * 用于创建空的用户对象，通常在从数据库查询后填充数据时使用
     */
    public Users() {
    }

    /**
     * 全参构造方法（不含 userId）
     * 用于创建新用户对象，通常在注册时使用
     * 
     * @param userName 用户名
     * @param password 密码
     * @param sex 性别
     * @param email 邮箱
     * @param role 角色
     */
    public Users(String userName, String password, String sex, String email, String role) {
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.role = role;
    }

    /**
     * 获取用户 ID
     * @return 用户 ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户 ID
     * @param userId 用户 ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取密码
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取性别
     * @return 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取邮箱
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取用户角色
     * @return 角色（admin 或 user）
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置用户角色
     * @param role 角色
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 重写 toString 方法，返回用户信息的字符串表示
     * 用于调试、日志记录等场景
     * 
     * @return 包含用户基本信息的字符串
     */
    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
