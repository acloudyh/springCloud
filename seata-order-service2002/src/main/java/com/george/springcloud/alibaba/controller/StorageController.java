package com.george.springcloud.alibaba.controller;

import com.george.springcloud.alibaba.domain.CommonResult;
import com.george.springcloud.alibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

@RestController
@Slf4j
public class StorageController {

    @Resource
    private StorageService storageService;

    @PostMapping(value = "storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId,
                                 @RequestParam("count") Integer count) {
        storageService.decrease(productId,count);
        return new CommonResult(200, "扣减库存成功");
    }

}
