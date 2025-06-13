package com.javaProject.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaProject.shortlink.admin.dao.entity.UserDO;
import com.javaProject.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.javaProject.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return 用户名存在，true；不存在，false
     */
    Boolean hasUserName(String username);

    void register(UserRegisterReqDTO requestParam);
}
