package com.saber.demojavafx.dto;

import com.saber.demojavafx.utils.PersianNationalCode;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Map;

@Data
public class PersonDto {
    private Integer id;
    @NotBlank(message = "نام الزامی است")
    @Size(max = 75,message = "نام حداکثر 75 حرف است")
    private String firstname;
    @NotBlank(message = "نام خانوادگی الزامی است")
    @Size(max = 85,message = "نام خانوادگی حداکثر 85 حرف است")
    private String lastname;
    @NotNull(message = "سن الزامی است")
    @Max(value = 120,message = "سن حداکثر 120 سال است")
    @Min(value = 1,message = "سن حداقل باید از 1 بیشتر باشد")
    private Integer age;
    @NotBlank(message = "موبایل الزامی است")
    @Size(max = 11,message = "شماره موبایل حداکثر 11 رقم است")
    @Pattern(regexp = "09\\d{9}",message = "شماره موبایل وارد شده نامعتبر است")
    private String mobile;
    @NotBlank(message = "کد ملی الزامی است")
    @Size(min = 10,max = 10,message = "کد ملی 10 رقم است")
    @PersianNationalCode(message = "کد ملی نامعتبر است")
    private String nationalCode;
    @NotBlank(message = "پست الکترونیک الزامی است")
    @Email(message = "پست الکترونیک نامعتبر است")
    private String email;
    private String createdAt;
    private String updatedAt;

    private Map<String,String> errors;
}