package com.javaProject.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaProject.shortlink.project.dao.entity.ShortLinkDO;
import com.javaProject.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.javaProject.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.javaProject.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.javaProject.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.javaProject.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.javaProject.shortlink.project.dto.resp.ShortLinkPageRespDTO;

import java.util.List;

/**
 * 短链接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {
    /**
     * 创建短链接
     * @param requestParam 创建短链接请求参数
     * @return 短链接创建信息
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam);

    /**
     * 分页查询短链接
     * @param requestParam 短链接分页查询请求参数
     * @return 短链接分页返回结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam);

    /**
     * 短链接分组内数量查询
     * @param requestParam 短链接分组内数量请求插叙参数
     * @return 短链接分组数量查询返回结果
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortlink(List<String> requestParam);

    /**
     * 修改短链接
     * @param requestParam 修改短链接请求参数
     */
    void updateShortLink(ShortLinkUpdateReqDTO requestParam);
}
