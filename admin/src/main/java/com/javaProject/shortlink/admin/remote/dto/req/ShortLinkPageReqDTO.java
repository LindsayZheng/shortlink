package com.javaProject.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 分页请求参数
 */
@Data
public class ShortLinkPageReqDTO extends Page {
    /**
     * 分组标识
     */
    private String gid;
}
