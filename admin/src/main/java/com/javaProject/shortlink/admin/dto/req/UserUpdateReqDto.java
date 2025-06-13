package com.javaProject.shortlink.admin.dto.req;

import lombok.Data;

@Data
public class UserUpdateReqDto {
    private String username;

    private String password;

    private String realName;

    private String phone;

    private String mail;
}