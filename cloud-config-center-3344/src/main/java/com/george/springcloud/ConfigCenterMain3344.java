package com.george.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Yang Hao
 * @description
 * @date 2020-09-19 14:29
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigCenterMain3344 {
    public static void main(String[] args) {

        SpringApplication.run(ConfigCenterMain3344.class);
    }
}
