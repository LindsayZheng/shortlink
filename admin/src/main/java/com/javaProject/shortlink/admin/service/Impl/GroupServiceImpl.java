package com.javaProject.shortlink.admin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaProject.shortlink.admin.common.biz.user.UserContext;
import com.javaProject.shortlink.admin.common.convention.result.Result;
import com.javaProject.shortlink.admin.dao.entity.GroupDO;
import com.javaProject.shortlink.admin.dao.mapper.GroupMapper;
import com.javaProject.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.javaProject.shortlink.admin.dto.req.ShortlinkGroupUpdateReqDTO;
import com.javaProject.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.javaProject.shortlink.admin.remote.dto.ShortLinkRemoteService;
import com.javaProject.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.javaProject.shortlink.admin.service.GroupService;
import com.javaProject.shortlink.admin.toolkit.RandomGenerator;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 短链接分组接口实现层
 */
@Service
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    /**
     * 后续重构为 springCloud Feign 调用
     */
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService(){};

    @Override
    public void saveGroup(String groupName){
        saveGroup(UserContext.getUsername(), groupName);
    }
    // 在创建用户时，提供 username 参数
    @Override
    public void saveGroup(String username, String groupName) {
        String gid;
        while(true){
            gid = RandomGenerator.generate();
            if (!hasGid(username, gid)) break;
        }
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .sortOrder(0)
                .username(UserContext.getUsername())
                .name(groupName)
                .build();
        baseMapper.insert(groupDO);
    }



    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        // TODO 获取用户名
        // String username = UserContext.getUsername();

        // 找到通过 username 找到当前用户创建的短视频分组集合，也就是该用户对应的 gid
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        // 通过 gid 找到当前用户的所有短链接
        Result<List<ShortLinkGroupCountQueryRespDTO>> listResult = shortLinkRemoteService
                .listGroupCountShortlink(groupDOList.stream().map(GroupDO::getGid).toList());
        // 将用户创建的分组集合转换成 shortLinkGroupRespDTO 类型的 list
        List<ShortLinkGroupRespDTO> shortLinkGroupRespDTOList = BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
        // 将每个分组的短链接数量（shortLinkCount）设置到对应的 ShortLinkGroupRespDTO 中
        // 1. forEach 遍历当前用户的所有分组 -> shortLinkGroupRespDTOList
        shortLinkGroupRespDTOList.forEach(each -> {
            // 2. getData() 返回分组短链接数量统计列表；stream() 转流
            Optional<ShortLinkGroupCountQueryRespDTO> first = listResult.getData().stream()
                    .filter(item -> Objects.equals(each.getGid(), item.getGid())).findFirst();
            first.ifPresent(item -> each.setShortLinkCount(first.get().getShortLinkCount()));
        });
        return shortLinkGroupRespDTOList;
    }

    @Override
    public void updateGroup(ShortlinkGroupUpdateReqDTO requestParam) {
        // String username = UserContext.getUsername();
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, requestParam.getGid())
                .eq(GroupDO::getDelFlag, 0);
        // 创建对象
        GroupDO groupDO = new GroupDO();
        // 获取新分组名
        groupDO.setName(requestParam.getName());
        baseMapper.update(groupDO,updateWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getDelFlag, 0);
        // 创建对象
        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO, updateWrapper);
    }

    @Override
    public void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam) {
        requestParam.forEach(each -> {
            GroupDO groupDO = GroupDO.builder()
                    .sortOrder(each.getSortOrder())
                    .build();
            LambdaUpdateWrapper<GroupDO> updateWrapperWrappers = Wrappers.lambdaUpdate(GroupDO.class)
                    .eq(GroupDO::getUsername, UserContext.getUsername())
                    .eq(GroupDO::getGid, UserContext.getUserId())
                    .eq(GroupDO::getDelFlag, 0);
            baseMapper.update(groupDO, updateWrapperWrappers);
        });
    }

    // 新增 username 参数
    private boolean  hasGid(String username, String gid)  {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                //todo 设置用户名
                // 进行判断，防止空值
                .eq(GroupDO::getUsername, Optional.ofNullable(username).orElse(UserContext.getUsername()));
        GroupDO hasGroupFlag = baseMapper.selectOne(queryWrapper);
        return hasGroupFlag != null;
    }
}
