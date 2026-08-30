package com.saber.demojavafx.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class IranianNationalCodeGenerator {
    private IranianNationalCodeGenerator() {
    }

    /**
     * تولید یک کد ملی 10 رقمی با رقم کنترل معتبر
     */
    public static String generate() {

        int[] digits = new int[10];

        // رقم اول را صفر تولید نمی‌کنیم
        digits[0] = ThreadLocalRandom.current().nextInt(1, 10);

        // 8 رقم بعدی
        for (int i = 1; i < 9; i++) {
            digits[i] = ThreadLocalRandom.current().nextInt(10);
        }
        digits[9] = calculateCheckDigit(digits);
        return digitsToString(digits);
    }

    /**
     * اعتبارسنجی کد ملی ایران
     */
    public static boolean isValid(String nationalCode) {

        if (nationalCode == null || !nationalCode.matches("\\d{10}")) {
            return false;
        }
        // جلوگیری از کدهایی مثل 0000000000
        boolean allSame = true;
        for (int i = 1; i < nationalCode.length(); i++) {
            if (nationalCode.charAt(i) != nationalCode.charAt(0)) {
                allSame = false;
                break;
            }
        }

        if (allSame) {
            return false;
        }
        int[] digits = new int[10];
        for (int i = 0; i < 10; i++) {
            digits[i] = Character.digit(nationalCode.charAt(i), 10);
        }
        int checkDigit = calculateCheckDigit(digits);

        return digits[9] == checkDigit;
    }

    /**
     * تولید تعداد مشخصی کد ملی بدون تکرار
     */
    public static Set<String> generateUnique(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count cannot be negative");
        }
        Set<String> result = new HashSet<>(count);
        while (result.size() < count) {
            result.add(generate());
        }
        return result;
    }

    /**
     * محاسبه رقم کنترل
     */
    private static int calculateCheckDigit(int[] digits) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int remainder = sum % 11;
        return remainder < 2
                ? remainder
                : 11 - remainder;
    }

    /**
     * تبدیل آرایه اعداد به String
     */
    private static String digitsToString(int[] digits) {

        StringBuilder result = new StringBuilder(10);
        for (int digit : digits) {
            result.append(digit);
        }
        return result.toString();
    }
}
