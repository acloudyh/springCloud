package com.george.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Yang Hao
 * @description
 * @date 2020-09-14 21:47
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaMainApplication7002 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMainApplication7002.class, args);
    }
}
