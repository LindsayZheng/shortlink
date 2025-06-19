package com.javaProject.shortlink.project.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ShortLinkUpdateReqDTO {
    private String fullShortUrl;

    private String originalUrl;

    /**
     * 原始 gid
     */
    private String originGid;

    /**
     * 修改后的 gid
     */
    private String gid;

    private Integer validDateType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validDate;

    private String describe;

}
