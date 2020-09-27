package com.george.springcloud.alibaba.service;

import java.math.BigDecimal;

/**
 * @author Yang Hao
 * @date 2020-09-27 11:58
 */
public interface AccountService {
    /**
     * 扣减账户余额
     *
     * @param userId
     * @param money
     */
    void decrease(Long userId, BigDecimal money);
}
