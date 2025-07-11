package com.javaProject.shortlink.admin.common.enums;

import com.javaProject.shortlink.admin.common.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnum implements IErrorCode {
    // 枚举错误码并进行封装
    USER_TOKEN_FAIL("A000200", "用户 TOKEN 验证失败"),
    USER_NULL("B000200", "用户记录不存在"),
    USER_NAME_EXIST("B000201", "用户名已存在"),
    USER_EXIST("B000202", "用户已存在"),
    USER_SAVE_ERROR("B000204", "用户记录新增失败"),
    USER_HAS_LOGGED_IN("B000205", "用户已登录"),
    USER_LOG_OUT_ERROR("B00206", "用户未登录或者 token 不存在");
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