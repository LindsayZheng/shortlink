package com.javaProject.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaProject.shortlink.admin.dao.entity.GroupDO;
import com.javaProject.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.javaProject.shortlink.admin.dto.req.ShortlinkGroupUpdateReqDTO;
import com.javaProject.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

/**
 * 短链接分组接口层
 */
public interface GroupService extends IService<GroupDO> {
    /**
     * 新增短链接分组
     * @param groupName 短链接分组名
     */
    void saveGroup(String groupName);

    /**
     * 新增短链接分组，用于用户注册时，配置默认分组
     * @param username 用户名
     * @param groupName 短链接分组名
     */
    void saveGroup(String username, String groupName);

    /**
     * 查询用户短链接分组集合
     * @return 用户短链接分组集合
     */
    List<ShortLinkGroupRespDTO> listGroup();

    /**
     * 修改短链接分组名
     * @param requestParam 修改短链接分组参数
     */
    void updateGroup(ShortlinkGroupUpdateReqDTO requestParam);

    /**
     * 删除短链接分组
     * @param gid 短链接分组标识
     */
    void deleteGroup(String gid);

    /**
     * 短链接分组排序
     * @param requestParam 短链接分组请求参数
     */
    void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam);
}
