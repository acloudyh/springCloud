package com.george.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return "----------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "----------testB";
    }

}
