package com.hx.util;

import java.util.Random;

/**
 * 算术验证码工具类 - 用于防止恶意登录
 * 
 * 作用：
 * 1. 生成简单的算术题目（加法、减法）
 * 2. 验证用户输入的答案是否正确
 * 3. 提供防暴力破解的安全机制
 * 
 * @author hx
 * @version 1.0
 */
public class CaptchaUtil {
    
    /**
     * 生成算术题目
     * 
     * @return String[] 数组，[0]为题目文本，[1]为正确答案
     */
    public static String[] generateCaptcha() {
        Random random = new Random();
        
        // 随机选择运算符：0为加法，1为减法
        int operator = random.nextInt(2);
        int num1, num2, answer;
        String question;
        
        if (operator == 0) {
            // 加法运算
            num1 = random.nextInt(20) + 1;  // 1-20
            num2 = random.nextInt(20) + 1;  // 1-20
            answer = num1 + num2;
            question = num1 + " + " + num2 + " = ?";
        } else {
            // 减法运算，确保结果不为负数
            num1 = random.nextInt(20) + 10; // 10-29
            num2 = random.nextInt(num1) + 1; // 1-num1
            answer = num1 - num2;
            question = num1 + " - " + num2 + " = ?";
        }
        
        return new String[]{question, String.valueOf(answer)};
    }
    
    /**
     * 验证答案是否正确
     * 
     * @param userAnswer 用户提交的答案
     * @param correctAnswer 正确答案
     * @return boolean 是否匹配
     */
    public static boolean verifyAnswer(String userAnswer, String correctAnswer) {
        if (userAnswer == null || correctAnswer == null) {
            return false;
        }
        return userAnswer.trim().equals(correctAnswer.trim());
    }
}