package com.saber.demojavafx.utils;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD,
        ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PersianNationalCodeValidator.class)
public @interface PersianNationalCode {
    String message() default "{validations.constraints.persian.nationalCode.format}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}