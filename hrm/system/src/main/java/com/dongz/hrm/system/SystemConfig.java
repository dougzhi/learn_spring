package com.dongz.hrm.system;

import com.dongz.hrm.common.interceptors.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author dong
 * @date 2020/2/23 20:58
 * @desc
 */
@Configuration
public class SystemConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                //指定拦截url
                .addPathPatterns("/**")
                .excludePathPatterns("/api/sys/login", "/api/sys/register/**");
    }
}
