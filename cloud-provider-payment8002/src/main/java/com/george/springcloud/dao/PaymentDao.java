package com.george.springcloud.dao;

import com.george.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yang Hao
 * @description
 * @date 2020-09-14 15:09
 */
@Mapper
public interface PaymentDao {
    int create(Payment payment);

    Payment getPaymentById(Long id);
}
