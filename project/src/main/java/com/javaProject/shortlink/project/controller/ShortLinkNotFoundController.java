package com.javaProject.shortlink.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.javaProject.shortlink.project.common.constant.ShortLinkNotFoundConstant.SHORT_LINK_NOT_FOUND;

/**
 * 短链接不存在跳转控制器
 */
@Controller
public class ShortLinkNotFoundController {
    /**
     * 短链接不存在跳转页面
     */
    @RequestMapping("/page/notfound")
    public String notfound() {
        return SHORT_LINK_NOT_FOUND;
    }
}
