package com.javaProject.shortlink.admin.remote.dto.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ShortLinkPageRespDTO {

    private Long id;

    private String domain;

    private String shortUri;

    private String fullShortUrl;

    private String originUrl;

    private String gid;

    private Integer validDateType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validDate;

    @TableField("`describe`")
    private String describe;

    private String favicon;
}
