package com.javaProject.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.javaProject.shortlink.admin.common.convention.result.Result;
import com.javaProject.shortlink.admin.common.convention.result.Results;
import com.javaProject.shortlink.admin.dto.req.UserLoginReqDTO;
import com.javaProject.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.javaProject.shortlink.admin.dto.resp.UserActualRespDTO;
import com.javaProject.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.javaProject.shortlink.admin.dto.resp.UserRespDTO;
import com.javaProject.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
用户控制管理层
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 只返回脱敏后的手机号
    @GetMapping("/api/shortlink/admin/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }

    // 返回真实手机号
    @GetMapping("/api/shortlink/admin/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDTO.class));
    }

    // 查询当前用户名是否存在
    @GetMapping("/api/shortlink/admin/v1/user/has-username")
    public Result<Boolean> hasUserName(@RequestParam("username") String username) {
        return Results.success(userService.hasUserName(username));
    }

    /**
     * 注册用户
     */
    @PostMapping("/api/shortlink/admin/v1/user/register")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam) {
        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 修改用户
     */
    @PostMapping("/api/shortlink/admin/v1/user/update")
    public Result<Void> update(@RequestBody UserRegisterReqDTO requestParam) {
        userService.update(requestParam);
        return Results.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/api/shortlink/admin/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam) {
        return Results.success(userService.login(requestParam));
    }

    /**
     * 检查用户是否登录
     */
    @GetMapping("/api/shortlink/admin/v1/user/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username, @RequestParam("token") String token) {
        return Results.success(userService.checkLogin(username, token));
    }

    /**
     * 用户退出登录
     */
    @DeleteMapping("/api/shortlink/admin/v1/user/logout")
    public Result<Void> logout(@RequestParam("username") String username, @RequestParam("token") String token) {
        userService.logout(username, token);
        return Results.success();
    }
}
