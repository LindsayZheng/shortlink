package com.javaProject.shortlink.project.dto.req;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaProject.shortlink.project.dao.entity.ShortLinkDO;
import lombok.Data;

/**
 * 分页请求参数
 */
@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {
    /**
     * 分组标识
     */
    private String gid;
}
