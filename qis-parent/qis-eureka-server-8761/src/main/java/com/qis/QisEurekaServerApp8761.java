package com.qis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author qishuo
 * @date 2021/5/30 3:54 下午
 */
@SpringBootApplication
@EnableEurekaServer
public class QisEurekaServerApp8761 {

    public static void main(String[] args) {
        SpringApplication.run(QisEurekaServerApp8761.class, args);
    }
}
