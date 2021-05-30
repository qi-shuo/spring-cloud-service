package com.qis.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qishuo
 * @date 2021/5/30 7:45 下午
 */
@Slf4j
public class RegisterBlackListFilter implements GatewayFilter, Ordered {
    /**
     * key:ip,key:时间戳单位秒,value请求的次数
     */
    private static Map<String, Map<Long, AtomicInteger>> cacheMap = new ConcurrentHashMap<>();
    /**
     * 最大请求次数
     */
    @Value("max.count")
    private int maxCount;

    /**
     * 请求时间单位秒
     */
    @Value("max.request.timeUnit")
    private int requestTimeUnit;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        if (Objects.isNull(remoteAddress)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            log.info("获取远程地址失败,直接返回");
            DataBuffer wrap = response.bufferFactory().wrap("获取远程地址失败,请重试".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(wrap));

        }
        String hostString = request.getRemoteAddress().getHostString();
        if (isIntercept(hostString)) {
            //访问拒接
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            log.info("=====>IP:{},频繁请求", hostString);
            String data = "请求被拒接!";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }
        return chain.filter(exchange);
    }

    /**
     * 是否拦截
     *
     * @param host
     * @return true需要拦截, false不用拦截
     */
    public boolean isIntercept(String host) {
        if (!cacheMap.containsKey(host)) {
            //进行初始化
            cacheMap.put(host, new ConcurrentHashMap<>() {
                {
                    put(getTime(), new AtomicInteger(1));
                }

            });
            return false;
        }
        Map<Long, AtomicInteger> longAtomicIntegerMap = cacheMap.get(host);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //打印监控情况,并清理超过1分钟的数据
        List<Long> clearKey = new ArrayList<>();
        longAtomicIntegerMap.forEach((time, count) -> {
            //当前时间
            long currTime = getTime();
            if (currTime - time > requestTimeUnit) {
                clearKey.add(currTime);
            } else {
                atomicInteger.incrementAndGet();
            }
        });
        //批量清理
        clearKey.forEach(longAtomicIntegerMap::remove);
        return atomicInteger.get() > maxCount;
    }

    /**
     * 获取秒级别的时间戳
     *
     * @return
     */
    private long getTime() {
        return System.currentTimeMillis() / 1000;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
