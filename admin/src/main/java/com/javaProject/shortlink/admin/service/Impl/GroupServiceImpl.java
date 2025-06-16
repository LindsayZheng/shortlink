package com.javaProject.shortlink.admin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaProject.shortlink.admin.common.biz.user.UserContext;
import com.javaProject.shortlink.admin.dao.entity.GroupDO;
import com.javaProject.shortlink.admin.dao.mapper.GroupMapper;
import com.javaProject.shortlink.admin.dto.req.ShortlinkGroupUpdateReqDTO;
import com.javaProject.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.javaProject.shortlink.admin.service.GroupService;
import com.javaProject.shortlink.admin.toolkit.RandomGenerator;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链接分组接口实现层
 */
@Service
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
    @Override
    public void saveGroup(String groupName) {
        String gid;
        while(true){
            gid = RandomGenerator.generate();
            if (!hasGid(gid)) break;
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
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
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

    private boolean  hasGid(String gid)  {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                //todo 设置用户名
                .eq(GroupDO::getUsername, null);
        GroupDO hasGroupFlag = baseMapper.selectOne(queryWrapper);
        return hasGroupFlag != null;
    }
}
