package com.javaProject.shortlink.admin.toolkit;

import java.util.Random;

/**
 * 生成随机生成 id
 */
public class RandomGenerator {
    public static String generate() {
        return generateRandomAlphanumeric(6);
    }

    public static String generateRandomAlphanumeric(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        // System.out.println(generateRandomAlphanumeric()); // 示例输出: "aB3x9Z"
    }
}