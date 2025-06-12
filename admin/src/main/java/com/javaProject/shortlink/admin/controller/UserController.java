package com.javaProject.shortlink.admin.controller;

import com.javaProject.shortlink.admin.common.convention.result.Result;
import com.javaProject.shortlink.admin.dto.resp.UserRespDTO;
import com.javaProject.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
用户控制管理层
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/shortlink/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        UserRespDTO result = userService.getUserByUsername(username);
        if (result == null) {
            return new Result<UserRespDTO>().setCode("-1").setMessage("result is Null");
        } else {
            return new Result<UserRespDTO>().setCode("0").setData(result);
        }
    }
}
