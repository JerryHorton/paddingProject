package com.example.padding.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.padding.common.JwtException;
import com.example.padding.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @Date 2023/7/1 11:37
 * @Description
 * @Author Sxy
 */

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        log.info("进入jwt拦截器");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        // 从http请求头中取出 token
        String token = request.getHeader("Authorization");
        if ("null".equals(token)) {
            throw new JwtException("用户未登录");
        }
        // 解析jwt串
        try {
            DecodedJWT decodedJWT = JwtUtils.verifyToken(token);
            request.setAttribute("userId", decodedJWT.getClaim("userId").asInt());
            request.setAttribute("username", decodedJWT.getClaim("username").asString());
        } catch (Exception e) {
            throw new JwtException("token异常");
        }
        return true;
    }
}
