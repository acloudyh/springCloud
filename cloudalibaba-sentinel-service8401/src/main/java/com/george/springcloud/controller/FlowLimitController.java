package com.george.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Yang Hao
 * @description
 * @date 2020-09-23 15:23
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "----------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        log.info("----------testB:[{}]", Thread.currentThread().getName());
        return "----------testB";
    }

    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        log.info("testD 测试RT");
        log.info("testD 异常比例");
        int age = 10 / 0;
        return "------testD";
    }

}
