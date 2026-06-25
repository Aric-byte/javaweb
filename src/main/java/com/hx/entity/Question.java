package com.hx.entity;

import java.io.Serializable;

/**
 * 试题实体类 - 用于在程序中表示考试题目信息
 * 
 * 作用：封装试题相关的数据，包括题目 ID、题干、四个选项和正确答案
 * 实现方法：
 * 1. 实现 Serializable 接口，支持对象序列化，便于在网络传输和会话存储中使用
 * 2. 提供私有属性存储试题数据
 * 3. 提供无参构造方法和全参构造方法
 * 4. 提供 getter/setter 方法用于访问和修改属性
 * 5. 重写 toString() 方法方便调试和日志输出
 * 
 * @author hx
 * @version 1.0
 */
public class Question implements Serializable {
    // 试题唯一标识符，对应数据库中的主键
    private Integer questionId;
    // 题目标干，包含问题描述
    private String title;
    // 选项 A 的内容
    private String optionA;
    // 选项 B 的内容
    private String optionB;
    // 选项 C 的内容
    private String optionC;
    // 选项 D 的内容
    private String optionD;
    // 正确答案，存储选项字母（A/B/C/D）
    private String answer;

    /**
     * 无参构造方法
     * 用于创建空的试题对象，通常在从数据库查询后填充数据时使用
     */
    public Question() {
    }

    /**
     * 全参构造方法（不含 questionId）
     * 用于创建新试题对象，通常在添加试题时使用
     * 
     * @param title 题目标干
     * @param optionA 选项 A
     * @param optionB 选项 B
     * @param optionC 选项 C
     * @param optionD 选项 D
     * @param answer 正确答案
     */
    public Question(String title, String optionA, String optionB, String optionC, String optionD, String answer) {
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
    }

    /**
     * 获取试题 ID
     * @return 试题 ID
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * 设置试题 ID
     * @param questionId 试题 ID
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * 获取题目标干
     * @return 题目标干
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置题目标干
     * @param title 题目标干
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取选项 A
     * @return 选项 A 的内容
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * 设置选项 A
     * @param optionA 选项 A 的内容
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    /**
     * 获取选项 B
     * @return 选项 B 的内容
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * 设置选项 B
     * @param optionB 选项 B 的内容
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    /**
     * 获取选项 C
     * @return 选项 C 的内容
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * 设置选项 C
     * @param optionC 选项 C 的内容
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    /**
     * 获取选项 D
     * @return 选项 D 的内容
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     * 设置选项 D
     * @param optionD 选项 D 的内容
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    /**
     * 获取正确答案
     * @return 正确答案（A/B/C/D）
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置正确答案
     * @param answer 正确答案
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 重写 toString 方法，返回试题信息的字符串表示
     * 用于调试、日志记录等场景
     * 
     * @return 包含试题基本信息的字符串
     */
    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", title='" + title + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
