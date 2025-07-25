package com.javaProject.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javaProject.shortlink.project.common.convention.result.Result;
import com.javaProject.shortlink.project.common.convention.result.Results;
import com.javaProject.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.javaProject.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.javaProject.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.javaProject.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.javaProject.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.javaProject.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.javaProject.shortlink.project.service.ShortLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后管：用户链接控制层
 */
@Api(tags = "短链接管理")
@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @GetMapping("/{short-uri}")
    public void restoreUrl(@PathVariable("short-uri") String shortUri, ServletRequest request, ServletResponse response) {
        shortLinkService.restoreUrl(shortUri, request, response);
    }

    /**
     * 创建短链接
     */
    @ApiOperation("创建短链接")
    @PostMapping("/api/shortlink/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return Results.success(shortLinkService.createShortLink(requestParam));
    }

    /**
     * 修改短链接
     */
    @PostMapping("/api/shortlink/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 分页查询短链接
     */
    @ApiOperation("分页查询短链接")
    @GetMapping("/api/shortlink/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return Results.success(shortLinkService.pageShortLink(requestParam));
    }

    /**
     * 查询短链接分组内数量
     */
    @ApiOperation("查询短链接分组内数量")
    @GetMapping("/api/shortlink/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLink(@RequestParam("requestParam") List<String> requestParam) {
        return Results.success(shortLinkService.listGroupShortlink(requestParam));

    }
}
