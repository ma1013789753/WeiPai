package com.jokerdata;

import com.jokerdata.common.config.LoginUserArgumentResolver;
import com.jokerdata.common.config.LoginInterceptor;
import com.jokerdata.common.config.WebAppConfigurer;
import com.jokerdata.service.app.SpareService;
import com.jokerdata.service.app.impl.SpareImpl;
import com.jokerdata.service.common.impl.AliSmsServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.List;

@SpringBootApplication
@EnableTransactionManagement //启动事物管理
@ServletComponentScan //阿里巴巴数据监控
@MapperScan("com.jokerdata.mapper")
@EnableScheduling//redis
@EnableCaching
@EnableAsync
public class AdminApplication extends WebAppConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);

    }


    @Bean
    LoginUserArgumentResolver currentUserArgumentResolver() {
        return new LoginUserArgumentResolver();
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @Login 注解 决定是否需要登录
    }


    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

}
