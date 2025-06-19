package com.javaProject.shortlink.admin.remote.dto;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javaProject.shortlink.admin.common.convention.result.Result;
import com.javaProject.shortlink.admin.dto.req.RecycleBinSaveReqDTO;
import com.javaProject.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.javaProject.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.javaProject.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.javaProject.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.javaProject.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.javaProject.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短链接中台远程调用服务
 */
public interface ShortLinkRemoteService {
    /**
     * 创建短链接
     * @param requestParam 创建短链接请求参数
     * @return 创建短链接相应
     */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO requestParam) {
        String resultBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/shortlink/v1/create", JSON.toJSONString(requestParam));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {});
    }

    /**
     * 查询短链接
     * @param requestParam 查询短链接请求参数
     * @return 短链接查询响应
     */

    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        Map<String, Object> requsetMap = new HashMap<>();
        requsetMap.put("gid", requestParam.getGid());
        requsetMap.put("current", requestParam.getCurrent());
        requsetMap.put("size", requestParam.getSize());
        // 结果为 JSON 字符串
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/shortlink/v1/page", requsetMap);
        // 使用 fastJSON 进行序列化
        return JSON.parseObject(resultPageStr, new TypeReference<>() {});
    }

    /**
     * 查询短链接数量
     * @param requestParam 查询短链接数量请求参数
     * @return 短链接数量查询响应
     */
    default Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupCountShortlink(List<String> requestParam) {
        Map<String, Object> requsetMap = new HashMap<>();
        requsetMap.put("requestParam", requestParam);
        String resultGroupCountStr = HttpUtil.get("http://127.0.0.1:8001/api/shortlink/v1/count", requsetMap);
        return JSON.parseObject(resultGroupCountStr, new TypeReference<>() {});
    }

    /**
     * 修改短链接
     * @param requestParam 修改短链接请求参数
     */
    default void updateShortLink(ShortLinkUpdateReqDTO requestParam) {
        HttpUtil.post("http://127.0.0.1:8001/api/shortlink/v1/update", JSON.toJSONString(requestParam));
    }

    /**
     * 根据 URL 获取标题
     *
     * @param url 目标网站地址
     * @return 网站标题
     */
    default Result<String> getTitleByUrl(@RequestParam("url") String url) {
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/shortlink/v1/title?url=" + url);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 保存回收站
     *
     * @param requestParam 请求参数
     */
    default void saveRecycleBin(RecycleBinSaveReqDTO requestParam) {
        HttpUtil.post("http://127.0.0.1:8001/api/shortlink/v1/recycle-bin/save", JSON.toJSONString(requestParam));
    }
}
