package com.qis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author qishuo
 * @date 2021/5/30 3:56 下午
 */
@SpringBootApplication
@EnableEurekaServer
public class QisEurekaServerApp8762 {
    public static void main(String[] args) {
        SpringApplication.run(QisEurekaServerApp8762.class, args);
    }
}
