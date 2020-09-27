package com.george.springcloud.alibaba.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Yang Hao
 * @date 2020-09-27 11:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private Long userId;
    private long productId;
    private Integer count;
    private BigDecimal money;
    /**
     * //订单状态:  0:创建    1:已完成
     */
    private Integer status;

}
