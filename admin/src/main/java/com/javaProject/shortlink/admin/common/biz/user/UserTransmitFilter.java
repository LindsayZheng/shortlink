package com.javaProject.shortlink.admin.common.biz.user;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * 用户信息传输过滤器
 *
 * @公众号：马丁玩编程，回复：加群，添加马哥微信（备注：12306）获取项目资料
 */
//@RequiredArgsConstructor
//public class UserTransmitFilter implements Filter {
//    private static final List<String> IGNORE_URI = Lists.newArrayList(
//            "/api/shortlink/admin/v1/user/login",
//            "/api/shortlink/admin/v1/user/has-username"
//    );
//
//    private final StringRedisTemplate stringRedisTemplate;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        String requestURI = httpServletRequest.getRequestURI();
//        if (!IGNORE_URI.contains(requestURI)) {
//            String method = httpServletRequest.getMethod();
//            if (!(Objects.equals(requestURI, "/api/shortlink/admin/v1/user")) && Objects.equals(method, "POST")) {
//                String username = httpServletRequest.getHeader("username");
//                String token = httpServletRequest.getHeader("token");
//                if (!StrUtil.isAllNotBlank(username, token)) {
//                    returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(Results.failure(new ClientException(USER_TOKEN_FAIL))));
//                    return;
//                }
//                Object userInfoJsonStr;
//                try {
//                    userInfoJsonStr = stringRedisTemplate.opsForHash().get("login_" + username, token);
//                    if (userInfoJsonStr == null) {
//                        throw new ClientException(USER_TOKEN_FAIL);
//                    }
//                } catch (Exception e) {
//                    returnJson((HttpServletResponse) servletResponse, JSON.toJSONString(Results.failure(new ClientException(USER_TOKEN_FAIL))));
//                    return;
//                }
//                UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDTO.class);
//                UserContext.setUser(userInfoDTO);
//            }
//        }
//        try {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } finally {
//            UserContext.removeUser();
//        }
//    }
//
//    private void returnJson(HttpServletResponse httpServletResponse, String json) throws IOException {
//        PrintWriter writer = null;
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        httpServletResponse.setContentType("text/html; charset=UTF-8");
//        try {
//            writer = httpServletResponse.getWriter();
//            writer.print(json);
//        } catch (Exception e) {
//
//        } finally {
//            if (writer != null) {
//                writer.close();
//            }
//        }
//    }
//}

@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String username = httpServletRequest.getHeader("username");
        if (StrUtil.isNotBlank(username)) {
            String userId = httpServletRequest.getHeader("userId");
            String realName = httpServletRequest.getHeader("realName");
            UserInfoDTO userInfoDTO = new UserInfoDTO(userId, username, realName);
            UserContext.setUser(userInfoDTO);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}