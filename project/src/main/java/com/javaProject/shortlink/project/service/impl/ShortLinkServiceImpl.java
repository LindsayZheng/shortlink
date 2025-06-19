package com.javaProject.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaProject.shortlink.project.common.convention.exception.ClientException;
import com.javaProject.shortlink.project.common.convention.exception.ServiceException;
import com.javaProject.shortlink.project.common.enums.ValiDateTypeEnum;
import com.javaProject.shortlink.project.dao.entity.ShortLinkDO;
import com.javaProject.shortlink.project.dao.mapper.ShortLinkMapper;
import com.javaProject.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.javaProject.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.javaProject.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.javaProject.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.javaProject.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.javaProject.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.javaProject.shortlink.project.service.ShortLinkService;
import com.javaProject.shortlink.project.toolkit.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 短链接接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    private final RBloomFilter<String> rBloomFilter;

//    private final ShortLinkMapper shortLinkMapper;

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam) {
        String shortLinkSuffix = generateSuffix(requestParam);
        String fullShortUrl =  StrBuilder.create(requestParam.getDomain())
                .append("/").append(shortLinkSuffix).toString();
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .domain(requestParam.getDomain())
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .createdType(requestParam.getCreatedType())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .describe(requestParam.getDescribe())
                .shortUri(shortLinkSuffix)
                .enableStatus(0)
                .fullShortUrl(fullShortUrl)
                .build();

        // 布隆过滤器误判，使用 try 捕捉误判导致重复入库的短链接
        try {
            baseMapper.insert(shortLinkDO);
        }
        catch (DuplicateKeyException e) {
            // 发生误判，查询数据库
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                            .eq(ShortLinkDO::getFullShortUrl, fullShortUrl);
            ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
            if (hasShortLinkDO != null) {
                log.warn("短链接 {} 重复入库", fullShortUrl);
                throw new ServiceException("短链接生成重复");
            }
        }
        // 布隆过滤器添加生成的短链接
        rBloomFilter.add(fullShortUrl);

        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .originUrl(shortLinkDO.getOriginUrl())
                .gid(shortLinkDO.getGid())
                .build();
    }

    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam) {
        LambdaQueryWrapper<ShortLinkDO> lambdaQueryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getDelFlag, 0)
                .orderByDesc(ShortLinkDO::getCreateTime);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(requestParam, lambdaQueryWrapper);
        return resultPage.convert(each -> BeanUtil.toBean(each, ShortLinkPageRespDTO.class));
    }

    @Override
    public List<ShortLinkGroupCountQueryRespDTO> listGroupShortlink(List<String> requestParam) {
        QueryWrapper<ShortLinkDO> queryWrapper = Wrappers.query(new ShortLinkDO())
                .select("gid as gid, count(*) as shortLinkCount")
                .in("gid", requestParam)
                .eq("enable_status", 0)
//                .eq("del_flag", 0)
//                .eq("del_time", 0L)
                .groupBy("gid");
        List<Map<String, Object>> shortLinkDOList = baseMapper.selectMaps(queryWrapper);
        return BeanUtil.copyToList(shortLinkDOList, ShortLinkGroupCountQueryRespDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShortLink(ShortLinkUpdateReqDTO requestParam) {
        // 根据原始 gid 找到当前表中的目标短链接分组
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                // 根据原始 gid 找到当前表中的目标短链接分组
                .eq(ShortLinkDO::getGid, requestParam.getOriginGid())
                .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0);
        ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
        // 目标修改的短链接不存在
        if (hasShortLinkDO == null) {
            throw new ClientException("目标短链接不存在");
        }
        // 修改后的 gid 与原始 gid 一致
        if (Objects.equals(hasShortLinkDO.getGid(), requestParam.getGid())) {
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .eq(ShortLinkDO::getDelTime, 0L)
                    // 如果日期类型为永久，则将有效期设置为空
                    .set(Objects.equals(requestParam.getValidDateType(), ValiDateTypeEnum.PERMANENT.getType()), ShortLinkDO::getValidDate, null);
            // 更新后的短链接分组
            ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                    .domain(hasShortLinkDO.getDomain())
                    .shortUri(hasShortLinkDO.getShortUri())
                    .favicon(hasShortLinkDO.getFavicon())
                    .createdType(hasShortLinkDO.getCreatedType())
                    .gid(requestParam.getGid())
                    .originUrl(requestParam.getOriginalUrl())
                    .describe(requestParam.getDescribe())
                    .validDateType(requestParam.getValidDateType())
                    .validDate(requestParam.getValidDate())
                    .build();
            // 更新短链接
            baseMapper.update(shortLinkDO, updateWrapper);
        } else {
            // 修改后的 gid 与原始 gid 不一致
            // 找到原短链接分组
            LambdaUpdateWrapper<ShortLinkDO> linkUpdateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, hasShortLinkDO.getGid())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getDelTime, 0L)
                    .eq(ShortLinkDO::getEnableStatus, 0);
            // 删除原短链接分组
            ShortLinkDO delShortLinkDO = ShortLinkDO.builder()
                    .delTime(System.currentTimeMillis())
                    .build();
            delShortLinkDO.setDelFlag(1);
            baseMapper.update(delShortLinkDO, linkUpdateWrapper);
            // 新建更新后的短链接分组
            ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                    .domain(hasShortLinkDO.getDomain())
                    .originUrl(requestParam.getOriginalUrl())
                    .gid(requestParam.getGid())
                    .createdType(hasShortLinkDO.getCreatedType())
                    .validDateType(requestParam.getValidDateType())
                    .validDate(requestParam.getValidDate())
                    .describe(requestParam.getDescribe())
                    .shortUri(hasShortLinkDO.getShortUri())
                    .enableStatus(hasShortLinkDO.getEnableStatus())
                    .fullShortUrl(hasShortLinkDO.getFullShortUrl())
                    .favicon(hasShortLinkDO.getFavicon())
                    .delTime(0L)
                    .build();
            // 插入表
            baseMapper.insert(shortLinkDO);
        }
    }

    private String generateSuffix(ShortLinkCreateReqDTO requestParam) {
        String originUrl = requestParam.getOriginUrl();
        String shortUri;
        // 发生哈希冲突时重试
        int customGenerateCount = 0;
        while (true) {
            // 当前生成短链接尝试次数
            if (customGenerateCount > 10) {
                throw new RuntimeException("短链接频繁生成，请稍后再试");
            }
            // 生成短链接
            shortUri = HashUtil.hashToBase62(originUrl);
            // 查询该短链接是否存在，短链接不存在，跳出循环
            if (!rBloomFilter.contains(requestParam.getDomain() + "/" + shortUri)) {
                break;
            }
            customGenerateCount++;
        }
        return HashUtil.hashToBase62(originUrl);
    }
}
