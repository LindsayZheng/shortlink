package com.javaProject.shortlink.admin.dto.resp;

import lombok.Data;

@Data
public class ShortLinkGroupRespDTO {
    private String gid;
    private String name;
    private String username;
    private Integer sortOrder;
}
