package com.javaProject.shortlink.admin.controller;

import com.javaProject.shortlink.admin.common.biz.user.UserContext;
import com.javaProject.shortlink.admin.common.convention.result.Result;
import com.javaProject.shortlink.admin.common.convention.result.Results;
import com.javaProject.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
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

    /**
     * 新增短链接分组
     */
    @PostMapping("/api/shortlink/admin/v1/group")
    public Result<Void> saveGroup(@RequestBody ShortlinkGroupSaveReqDTO requestParam) {
        String username = UserContext.getUsername();
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }

    /**
     * 查询短链接分组集合
     */
    @GetMapping("/api/shortlink/admin/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup() {
        String username = UserContext.getUsername();
        return Results.success(groupService.listGroup());
    }

    /**
     * 修改短链接分组名称
     */
    @PutMapping("/api/shortlink/admin/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortlinkGroupUpdateReqDTO requestParam) {
        groupService.updateGroup(requestParam);
        return Results.success();
    }

    /**
     * 删除短链接分组名称
     */
    @DeleteMapping("/api/shortlink/admin/v1/group")
    public Result<Void>  deleteGroup(@RequestParam String gid) {
        groupService.deleteGroup(gid);
        return Results.success();
    }

    /**
     * 排序短链接
     */
    @PostMapping("/api/shortlink/admin/v1/group/sort")
    public Result<Void>  sortGroup(@RequestBody List<ShortLinkGroupSortReqDTO> requestParam) {
        groupService.sortGroup(requestParam);
        return Results.success();
    }


}
