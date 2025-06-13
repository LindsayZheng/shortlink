package com.javaProject.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaProject.shortlink.admin.dao.entity.UserDO;
import com.javaProject.shortlink.admin.dto.req.UserLoginReqDTO;
import com.javaProject.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.javaProject.shortlink.admin.dto.resp.UserLoginRespDTO;
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

    /**
     * 注册用户
     * @param requestParam 用户请求参数
     */
    void register(UserRegisterReqDTO requestParam);

    /**
            *      * 修改用户
     *      * @param requestParam 修改用户请求参数
     *      */
    void update(UserRegisterReqDTO requestParam);

    /**
     * 用户登录
     *
     * @param requestParam 用户登录请求参数
     * @return 用户登录返回参数
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 检查用户是否登录
     * @param  username 用户名
     * @param token 用户登录 token
     * @return 用户是否登录核实
     */
    Boolean checkLogin(String username, String token);
}
