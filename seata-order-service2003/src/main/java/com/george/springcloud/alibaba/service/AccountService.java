package com.george.springcloud.alibaba.service;

import java.math.BigDecimal;

/**
 * @author Yang Hao
 * @date 2020-09-27 11:58
 */
public interface AccountService {
    void decrease(Long userId, BigDecimal money);
}
