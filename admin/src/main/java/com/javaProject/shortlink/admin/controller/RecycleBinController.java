package com.javaProject.shortlink.admin.controller;

import com.javaProject.shortlink.admin.common.convention.result.Result;
import com.javaProject.shortlink.admin.common.convention.result.Results;
import com.javaProject.shortlink.admin.remote.dto.ShortLinkRemoteService;
import com.javaProject.shortlink.admin.dto.req.RecycleBinSaveReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回收站管理控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    /**
     * 后续重构为 SpringCloud Feign 调用
     */
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 保存回收站
     */
    @PostMapping("/api/shortlink/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        shortLinkRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }
}