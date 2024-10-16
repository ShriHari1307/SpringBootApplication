package com.example.springProject.Configuration;

import com.example.springProject.Interceptors.HeaderValidationInterceptor;
import com.example.springProject.Interceptors.LoggingInterceptor;
import com.example.springProject.Interceptors.RoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    HeaderValidationInterceptor headerValidationInterceptor;
    @Autowired
    LoggingInterceptor loggingInterceptor;
    @Autowired
    RoleInterceptor roleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerValidationInterceptor).addPathPatterns("/**").order(2);
        registry.addInterceptor(loggingInterceptor).order(1);
        registry.addInterceptor(roleInterceptor).addPathPatterns("/provider/create", "/provider/DeleteProvider/**", "/provider/update/**",
                "/agent/create/**", "/agent/update/**", "/agent/deleteAgent/**").order(3);
    }
}
