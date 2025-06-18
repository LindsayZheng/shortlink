package com.javaProject.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javaProject.shortlink.admin.common.convention.result.Result;
import com.javaProject.shortlink.admin.remote.dto.ShortLinkRemoteService;
import com.javaProject.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.javaProject.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.javaProject.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.javaProject.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接后管控制层
 */
@RestController
@Api(tags = "短链接管理")
public class ShortLinkController {

    /**
     * 后续重构为 springCloud Feign 调用
     */
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService(){};

    /**
     * 创建短链接
     */
    @ApiOperation("创建短链接")
    @PostMapping("/api/shortlink/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return shortLinkRemoteService.createShortLink(requestParam);
    }

    /**
     * 分页查询短链接
     */
    @ApiOperation("分页查询短链接")
    @GetMapping("/api/shortlink/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return shortLinkRemoteService.pageShortLink(requestParam);
    }
}
