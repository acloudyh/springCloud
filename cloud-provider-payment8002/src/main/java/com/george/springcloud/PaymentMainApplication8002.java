package com.george.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Yang Hao
 * @date 2020/9/14
 */
@SpringBootApplication
@EnableEurekaClient
public class PaymentMainApplication8002 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMainApplication8002.class, args);
    }
}
