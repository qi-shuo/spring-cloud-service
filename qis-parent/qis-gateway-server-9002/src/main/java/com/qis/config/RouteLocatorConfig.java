package com.qis.config;

import com.qis.filter.RegisterBlackListFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author qishuo
 * @date 2021/5/30 9:12 下午
 */
@Configuration
public class RouteLocatorConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(r ->
                r.path("/api/user/register/**")
                        //转发路由
                        .uri("lb://qis-server-user")
                        //注册自定义过滤器
                        .filters(new RegisterBlackListFilter())
                        //给定id
                        .id("register-black"))
                .build();
    }

}
