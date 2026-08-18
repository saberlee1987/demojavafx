package com.saber.demojavafx.dto;

import lombok.Data;

@Data
public class PersonDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private Integer age;
    private String mobile;
    private String nationalCode;
    private String email;
    private String createdAt;
    private String updatedAt;
}