package com.javaProject.shortlink.admin.config;

import com.javaProject.shortlink.admin.common.biz.user.UserTransmitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户配置自动装配
 */
@Configuration
public class UserConfiguration {

//    /**
//     * 用户信息传递过滤器
//     */
//    @Bean
//    public FilterRegistrationBean<UserTransmitFilter> globalUserTransmitFilter(StringRedisTemplate stringRedisTemplate) {
//        FilterRegistrationBean<UserTransmitFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new UserTransmitFilter(stringRedisTemplate));
//        registration.addUrlPatterns("/*");
//        registration.addInitParameter("login", "/api/shortlink/v1/user/login");
//        registration.setOrder(0);
//        return registration;
//    }

    /**
     * 用户信息传递过滤器
     */
    @Bean
    public FilterRegistrationBean<UserTransmitFilter> globalUserTransmitFilter() {
        FilterRegistrationBean<UserTransmitFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new UserTransmitFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(0);
        return registration;
    }
}