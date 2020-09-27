package com.george.springcloud.alibaba.service.impl;

import com.george.springcloud.alibaba.dao.AccountDao;
import com.george.springcloud.alibaba.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 账户业务实现类
 *
 * @author Yang Hao
 * @date 2020-09-27 11:58
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("------------->account-service 中扣减库存开始");
        //  模拟异常 配合 2001 服务里面的注解    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
        //  导致结果,订单状态没变成已完成,库存和账户都被扣减
        //  正常测试注销try catch.
//        try {
//            TimeUnit.SECONDS.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        accountDao.decrease(userId, money);
        log.info("------------->account-service 中扣减库存结束");
    }
}
