package com.boilerplate.demo.security;

import com.boilerplate.demo.interceptor.LogRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiEndpointConfiguration implements WebMvcConfigurer {

    @Autowired
    private LogRequestInterceptor logRequestInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseSuffixPatternMatch(false);
        matcher.setUseRegisteredSuffixPatternMatch(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logRequestInterceptor).order(0);
    }
}