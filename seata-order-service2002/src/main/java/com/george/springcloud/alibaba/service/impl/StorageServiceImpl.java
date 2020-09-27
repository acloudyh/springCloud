package com.george.springcloud.alibaba.service.impl;

import com.george.springcloud.alibaba.dao.StorageDao;
import com.george.springcloud.alibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Yang Hao
 * @date 2020-09-27 11:58
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        storageDao.decrease(productId, count);
    }

}
