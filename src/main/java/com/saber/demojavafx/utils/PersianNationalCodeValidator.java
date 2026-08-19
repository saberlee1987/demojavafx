package com.saber.demojavafx.utils;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersianNationalCodeValidator implements ConstraintValidator<PersianNationalCode, String> {
    @Override
    public boolean isValid(String nationalCodeString, ConstraintValidatorContext context) {
        boolean isValid;
        if (nationalCodeString == null || nationalCodeString.isEmpty())
            return false;
        if (nationalCodeString.length() != 10)
            return false;
        long nationalCode = Long.parseLong(nationalCodeString);
        byte[] arrayNationalCode = new byte[10];
        //extract digits from number
        for (int i = 0; i < 10; i++) {
            arrayNationalCode[i] = (byte) (nationalCode % 10);
            nationalCode = nationalCode / 10;
        }
        //Checking the control digit
        int sum = 0;
        for (int i = 9; i > 0; i--)
            sum += arrayNationalCode[i] * (i + 1);
        int temp = sum % 11;
        if (temp < 2)
            isValid = arrayNationalCode[0] == temp;
        else
            isValid = arrayNationalCode[0] == 11 - temp;

        return isValid;
    }
}
