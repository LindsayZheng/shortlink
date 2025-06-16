package com.javaProject.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 短链接分组排序参数
 */
@Data
public class ShortLinkGroupSortReqDTO {
    /**
     * 分组 id
     */
    private String gid;

    /**
     * 排序字段
     */
    private Integer sortOrder;
}
