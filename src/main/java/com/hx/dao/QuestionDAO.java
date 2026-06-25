package com.hx.dao;

import com.hx.entity.Question;
import com.hx.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 试题数据访问层（DAO） - 负责对试题数据进行数据库操作
 * 
 * 作用：
 * 1. 封装所有与 question 表相关的数据库操作
 * 2. 提供试题的增删改查、随机抽取等功能
 * 3. 将数据库操作细节与业务逻辑分离，提高代码可维护性
 * 
 * 实现方法：
 * 1. 使用 JdbcUtil 获取和关闭数据库连接
 * 2. 使用 PreparedStatement 执行参数化 SQL 语句，防止 SQL 注入
 * 3. 使用 ResultSet 处理查询结果
 * 4. 将数据库记录映射为 Question 实体对象
 * 5. 使用 Collections.shuffle 实现随机抽题
 * 
 * @author hx
 * @version 1.0
 */
public class QuestionDAO {
    
    /**
     * 添加试题
     * 
     * 作用：将新试题插入到数据库中
     * 实现方法：
     * 1. 编写 INSERT SQL 语句
     * 2. 使用 PreparedStatement 设置试题的各个参数
     * 3. 执行插入操作，返回受影响的行数
     * 4. 根据返回值判断是否添加成功
     * 
     * @param question 试题对象，包含待添加的试题信息
     * @return boolean 添加成功返回 true，失败返回 false
     */
    public boolean add(Question question) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 插入语句：向 question 表插入新记录，questionId 为自增主键，无需手动设置
            String sql = "INSERT INTO question (title, optionA, optionB, optionC, optionD, answer) VALUES (?, ?, ?, ?, ?, ?)";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 按顺序设置参数
            ps.setString(1, question.getTitle());   // 题干
            ps.setString(2, question.getOptionA()); // 选项 A
            ps.setString(3, question.getOptionB()); // 选项 B
            ps.setString(4, question.getOptionC()); // 选项 C
            ps.setString(5, question.getOptionD()); // 选项 D
            ps.setString(6, question.getAnswer());  // 正确答案
            
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
     * 根据 ID 查询试题
     * 
     * 作用：通过试题 ID 查询试题的详细信息
     * 实现方法：
     * 1. 编写 SELECT SQL 语句，使用 WHERE 条件匹配 questionId
     * 2. 使用 PreparedStatement 设置 questionId 参数
     * 3. 执行查询，从结果集中获取试题信息
     * 4. 创建并返回 Question 对象
     * 
     * @param questionId 试题 ID，主键
     * @return Question 试题对象；如果未找到试题返回 null
     */
    public Question findById(int questionId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Question question = null;
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 查询语句：根据试题 ID 查询
            String sql = "SELECT * FROM question WHERE questionId = ?";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 设置参数：试题 ID
            ps.setInt(1, questionId);
            // 执行查询
            rs = ps.executeQuery();
            
            // 如果结果集有数据
            if (rs.next()) {
                // 创建试题对象
                question = new Question();
                // 从结果集中获取各字段值
                question.setQuestionId(rs.getInt("questionId"));
                question.setTitle(rs.getString("title"));
                question.setOptionA(rs.getString("optionA"));
                question.setOptionB(rs.getString("optionB"));
                question.setOptionC(rs.getString("optionC"));
                question.setOptionD(rs.getString("optionD"));
                question.setAnswer(rs.getString("answer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            JdbcUtil.closeAll(rs, ps, conn);
        }
        
        return question;
    }
    
    /**
     * 查询所有试题
     * 
     * 作用：获取数据库中所有试题的列表
     * 实现方法：
     * 1. 编写 SELECT ALL SQL 语句，按 questionId 排序
     * 2. 执行查询，遍历结果集
     * 3. 对每条记录创建 Question 对象并添加到列表中
     * 4. 返回试题列表
     * 
     * @return List<Question> 包含所有试题的 ArrayList
     */
    public List<Question> findAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // 创建 ArrayList 存储试题列表
        List<Question> questionList = new ArrayList<>();
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 查询语句：查询所有试题，按 questionId 升序排列
            String sql = "SELECT * FROM question ORDER BY questionId";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 执行查询
            rs = ps.executeQuery();
            
            // 遍历结果集
            while (rs.next()) {
                // 为每条记录创建试题对象
                Question question = new Question();
                // 从结果集中获取各字段值
                question.setQuestionId(rs.getInt("questionId"));
                question.setTitle(rs.getString("title"));
                question.setOptionA(rs.getString("optionA"));
                question.setOptionB(rs.getString("optionB"));
                question.setOptionC(rs.getString("optionC"));
                question.setOptionD(rs.getString("optionD"));
                question.setAnswer(rs.getString("answer"));
                // 将试题对象添加到列表中
                questionList.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            JdbcUtil.closeAll(rs, ps, conn);
        }
        
        return questionList;
    }
    
    /**
     * 随机获取指定数量的试题
     * 
     * 作用：从题库中随机抽取指定数量的试题，用于组卷
     * 实现方法：
     * 1. 调用 findAll() 方法获取所有试题
     * 2. 如果试题总数小于等于需要的数量，直接返回所有试题
     * 3. 使用 Collections.shuffle() 方法打乱试题列表顺序
     * 4. 使用 subList() 方法截取前 count 个试题
     * 5. 返回随机抽取的试题列表
     * 
     * @param count 需要抽取的试题数量
     * @return List<Question> 包含随机抽取的试题列表
     */
    public List<Question> getRandomQuestions(int count) {
        // 获取所有试题
        List<Question> allQuestions = findAll();
        // 如果试题总数小于等于需要的数量，直接返回所有试题
        if (allQuestions.size() <= count) {
            return allQuestions;
        }
        
        // 使用 Collections.shuffle 方法随机打乱列表顺序
        // 该方法基于 Fisher-Yates 洗牌算法，确保随机性
        Collections.shuffle(allQuestions);
        // 返回前 count 个试题
        return allQuestions.subList(0, count);
    }
    
    /**
     * 更新试题
     * 
     * 作用：修改数据库中指定试题的信息
     * 实现方法：
     * 1. 编写 UPDATE SQL 语句，使用 WHERE 条件匹配 questionId
     * 2. 使用 PreparedStatement 设置新的试题信息参数
     * 3. 执行更新操作，返回受影响的行数
     * 4. 根据返回值判断是否更新成功
     * 
     * @param question 试题对象，包含更新后的试题信息（questionId 用于定位记录）
     * @return boolean 更新成功返回 true，失败返回 false
     */
    public boolean update(Question question) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 更新语句：根据 questionId 更新试题信息
            String sql = "UPDATE question SET title = ?, optionA = ?, optionB = ?, optionC = ?, optionD = ?, answer = ? WHERE questionId = ?";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 按顺序设置参数
            ps.setString(1, question.getTitle());   // 新题干
            ps.setString(2, question.getOptionA()); // 新选项 A
            ps.setString(3, question.getOptionB()); // 新选项 B
            ps.setString(4, question.getOptionC()); // 新选项 C
            ps.setString(5, question.getOptionD()); // 新选项 D
            ps.setString(6, question.getAnswer());  // 新答案
            ps.setInt(7, question.getQuestionId()); // 试题 ID（WHERE 条件）
            
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
     * 删除试题
     * 
     * 作用：从数据库中删除指定试题
     * 实现方法：
     * 1. 编写 DELETE SQL 语句，使用 WHERE 条件匹配 questionId
     * 2. 使用 PreparedStatement 设置 questionId 参数
     * 3. 执行删除操作，返回受影响的行数
     * 4. 根据返回值判断是否删除成功
     * 
     * @param questionId 要删除的试题 ID
     * @return boolean 删除成功返回 true，失败返回 false
     */
    public boolean delete(int questionId) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            // 获取数据库连接
            conn = JdbcUtil.getConnection();
            // SQL 删除语句：根据试题 ID 删除记录
            String sql = "DELETE FROM question WHERE questionId = ?";
            // 创建预编译语句对象
            ps = conn.prepareStatement(sql);
            // 设置参数：试题 ID
            ps.setInt(1, questionId);
            
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
