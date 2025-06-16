package com.javaProject.shortlink.admin.controller;

import com.javaProject.shortlink.admin.common.convention.result.Result;
import com.javaProject.shortlink.admin.common.convention.result.Results;
import com.javaProject.shortlink.admin.dto.req.ShortlinkGroupSaveReqDTO;
import com.javaProject.shortlink.admin.dto.req.ShortlinkGroupUpdateReqDTO;
import com.javaProject.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.javaProject.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接分组控制层
 */
@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/api/shortlink/v1/group")
    public Result<Void> save(@RequestBody ShortlinkGroupSaveReqDTO requestParam) {
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }

    /**
     * 查询短链接分组集合
     */
    @GetMapping("/api/shortlink/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup() {
        return Results.success(groupService.listGroup());
    }

    /**
     * 修改短链接分组名称
     */
    @PutMapping("/api/shortlink/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortlinkGroupUpdateReqDTO requestParam) {
        groupService.updateGroup(requestParam);
        return Results.success();
    }
}
