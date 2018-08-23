package com.fqxyi.statisstageserver.module.clickNum.service.impl;

import com.fqxyi.statisstageserver.common.exception.ExceptionConstant;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumBean;
import com.fqxyi.statisstageserver.module.clickNum.service.ClickNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ShenBF
 * @desc
 * @date 2018/8/21
 */
@Service
public class ClickNumServiceImpl implements ClickNumService {

    /**
     * 缓存点击次数
     */
    private int num = 0;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int set(String name) {
        try {
            //从redis中获取数据
            ClickNumBean bean = (ClickNumBean) redisTemplate.opsForValue().get(name);
            if (bean == null) {
                num = 0;
                bean = new ClickNumBean();
                bean.setDate(new Date().toString());
                bean.setName(name);
                bean.setNum(++num);
            } else {
                bean.setNum(++num);
            }
            //保存数据到redis中
            redisTemplate.opsForValue().set(name, bean);
            return num;
        } catch (Exception e) {
            return ExceptionConstant.CODE_ERR_EX;
        }
    }

    @Override
    public int get(String name) {
        try {
            //从redis中获取数据
            ClickNumBean bean = (ClickNumBean) redisTemplate.opsForValue().get(name);
            if (bean == null) {
                return ExceptionConstant.CODE_ERR_DATA;
            } else {
                return bean.getNum();
            }
        } catch (Exception e) {
            return ExceptionConstant.CODE_ERR_EX;
        }
    }

    @Override
    public int del(String name) {
        try {
            //保存数据到redis中
            redisTemplate.opsForValue().set(name, null);
            return ExceptionConstant.CODE_OK;
        } catch (Exception e) {
            return ExceptionConstant.CODE_ERR_EX;
        }
    }

}
