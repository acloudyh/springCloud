package com.george.springcloud.alibaba.controller;

import com.george.springcloud.alibaba.domain.CommonResult;
import com.george.springcloud.alibaba.domain.Order;
import com.george.springcloud.alibaba.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Yang Hao
 * @date 2020-09-27 11:48
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public CommonResult create(Order order) {
        orderService.create(order);
        return new CommonResult(200, "订单创建完成");
    }


}
