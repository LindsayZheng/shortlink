package com.javaProject.shortlink.admin.common.enums;

import com.javaProject.shortlink.admin.common.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnum implements IErrorCode {
    // 枚举错误码并进行封装
    USER_NULL("B000200", "用户记录不存在"),
    USER_EXIST("B000201", "用户记录已存在");
    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}