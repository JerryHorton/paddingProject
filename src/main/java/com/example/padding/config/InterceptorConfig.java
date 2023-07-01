package com.example.padding.config;

import com.example.padding.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version 1.0
 * @Date 2023/7/1 11:50
 * @Description
 * @Author Sxy
 */

@Slf4j
@EnableWebMvc   //WebMvcConfigurer 和 WebMvcConfigurationSupport同时实现只会生效一个，需要手动配置
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("配置拦截器");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/**/*.js", "/**/*.css");
    }
}
