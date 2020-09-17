package com.george.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author Yang Hao
 * @description
 * @date 2020-09-16 16:38
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "-------PaymentFallbackService fall back-[paymentInfo_OK],o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "-------PaymentFallbackService fall back-[paymentInfo_TimeOut],o(╥﹏╥)o";
    }
}
