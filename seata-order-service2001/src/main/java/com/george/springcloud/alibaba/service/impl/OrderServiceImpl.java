package com.george.springcloud.alibaba.service.impl;

import com.george.springcloud.alibaba.dao.OrderDao;
import com.george.springcloud.alibaba.domain.Order;
import com.george.springcloud.alibaba.service.AccountService;
import com.george.springcloud.alibaba.service.OrderService;
import com.george.springcloud.alibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Yang Hao
 * @date 2020-09-27 11:31
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;

    @Override
    public void create(Order order) {

    }
}
