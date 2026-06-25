-- 创建数据库
CREATE DATABASE IF NOT EXISTS exam_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE exam_system;

-- 创建用户信息表
DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
    userId INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户编号',
    userName VARCHAR(50) NOT NULL COMMENT '用户名称',
    password VARCHAR(50) NOT NULL COMMENT '用户密码',
    sex CHAR(1) DEFAULT '男' COMMENT '用户性别 男或女',
    email VARCHAR(50) COMMENT '用户邮箱',
    role VARCHAR(20) DEFAULT 'user' COMMENT '用户角色：admin 或 user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- 创建试题信息表
DROP TABLE IF EXISTS question;
CREATE TABLE question(
    questionId INT PRIMARY KEY AUTO_INCREMENT COMMENT '题目编号',
    title VARCHAR(200) NOT NULL COMMENT '题目内容',
    optionA VARCHAR(50) NOT NULL COMMENT '选项 A',
    optionB VARCHAR(50) NOT NULL COMMENT '选项 B',
    optionC VARCHAR(50) NOT NULL COMMENT '选项 C',
    optionD VARCHAR(50) NOT NULL COMMENT '选项 D',
    answer CHAR(1) NOT NULL COMMENT '正确答案：A/B/C/D'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试题信息表';

-- 插入测试数据 - 管理员账户
INSERT INTO Users (userName, password, sex, email, role) VALUES 
('admin', 'admin123', '男', 'admin@exam.com', 'admin');

-- 插入测试数据 - 普通用户
INSERT INTO Users (userName, password, sex, email, role) VALUES 
('student1', '123456', '男', 'student1@test.com', 'user'),
('student2', '123456', '女', 'student2@test.com', 'user');

-- 插入测试试题数据
INSERT INTO question (title, optionA, optionB, optionC, optionD, answer) VALUES
('10 - 8 = ?', '9', '1', '2', '0', 'C'),
('5 + 7 = ?', '10', '12', '15', '8', 'B'),
('3 * 4 = ?', '7', '10', '12', '14', 'C'),
('20 / 5 = ?', '3', '4', '5', '6', 'B'),
('15 - 6 = ?', '7', '8', '9', '10', 'C'),
('2 + 3 * 4 = ?', '14', '20', '12', '18', 'A'),
('100 / 25 = ?', '3', '4', '5', '6', 'B'),
('8 + 9 = ?', '15', '16', '17', '18', 'C'),
('25 - 13 = ?', '10', '11', '12', '13', 'C'),
('6 * 7 = ?', '36', '42', '48', '54', 'B');
