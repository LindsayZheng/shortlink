package com.javaProject.shortlink.project.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 有效期类型枚举
 */
@RequiredArgsConstructor
public enum ValiDateTypeEnum {
    /**
     * 永久有效期类型
     */
    PERMANENT(0),

    /**
     * 自定义有效期类型
     */
    CUSTOM(1);

    @Getter
    private final int type;
}
