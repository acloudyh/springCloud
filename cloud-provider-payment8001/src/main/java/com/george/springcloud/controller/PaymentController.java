package com.george.springcloud.controller;

import com.george.springcloud.entities.CommonResult;
import com.george.springcloud.entities.Payment;
import com.george.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Yang Hao
 * @description
 * @date 2020-09-14 16:10
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("-------------插入结果:[{}]", result);
        if (result > 0) {
            return new CommonResult(200, "插入数据库成功,端口号: " + serverPort, result);
        } else {
            return new CommonResult(444, "插入数据库失败", result);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("-------------查询结果:[{}]", payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功,端口号: " + serverPort, payment);
        } else {
            return new CommonResult(444, "没有对应记录,查询 ID: " + id, null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object getDiscovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("----------element:" + service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("serviceId:[{}], 主机名称:[{}], 端口号:[{}], url地址:[{}]", instance.getServiceId(), instance.getHost(), instance.getPort(), instance.getUri());
        }
        return this.discoveryClient;
    }

    /**
     * ribbon负载均衡 rule
     *
     * @return
     */
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }


    /**
     * feign超时测试
     *
     * @return
     */
    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeOut() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    /**
     * P94用,sleuth+zipkin链路监控使用
     *
     * @return
     */
    @GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        return "hi,I am payment_zipkin server fall back, welcome to";
    }


}
