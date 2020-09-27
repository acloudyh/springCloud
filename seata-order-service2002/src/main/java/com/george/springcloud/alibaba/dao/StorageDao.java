package com.george.springcloud.alibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Yang Hao
 * @date 2020-09-27 12:01
 */
@Mapper
public interface StorageDao {
    int decrease(@Param("productId") Long productId, @Param("count") Integer count);

}
