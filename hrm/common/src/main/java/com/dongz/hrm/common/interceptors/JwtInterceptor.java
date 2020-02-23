package com.dongz.hrm.common.interceptors;

import com.dongz.hrm.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dong
 * @date 2020/2/23 20:37
 * @desc 统一用户权限校验
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        Assert.hasText(token, "签名信息为空，请求重新登录！");

        Claims claims = jwtUtils.parseJwt(token);
        Assert.notNull(claims, "用户登录信息异常， 请重新登录！");
        request.setAttribute("user_claims", claims);

        return true;
    }
}
