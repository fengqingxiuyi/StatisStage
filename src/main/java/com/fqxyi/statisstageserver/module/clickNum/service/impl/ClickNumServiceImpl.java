package com.fqxyi.statisstageserver.module.clickNum.service.impl;

import com.fqxyi.statisstageserver.common.exception.ExceptionConstant;
import com.fqxyi.statisstageserver.common.util.TextUtil;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumBean;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumDateBean;
import com.fqxyi.statisstageserver.module.clickNum.service.ClickNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ShenBF
 * @desc 点击次数实现类
 * @date 2018/8/21
 */
@Service
public class ClickNumServiceImpl implements ClickNumService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ClickNumBean set(String name) {
        try {
            String date = sdf.format(new Date());
            //从redis中获取数据
            ClickNumBean bean = (ClickNumBean) redisTemplate.opsForValue().get(name);
            if (bean == null || bean.getDateBean() == null || bean.getDateBean().size() == 0) { //数据不存在
                ClickNumDateBean dateBean = new ClickNumDateBean();
                dateBean.setDate(date);
                dateBean.setNum(1);
                bean = new ClickNumBean();
                bean.setName(name);
                List<ClickNumDateBean> dateBeanList = new ArrayList<>();
                dateBeanList.add(dateBean);
                bean.setDateBean(dateBeanList);
            } else { //数据存在
                int index = 0; //用于判断当前date是否需要新增还是已存在
                for (int i = 0; i < bean.getDateBean().size(); i++) {
                    if (bean.getDateBean().get(i) == null) { //脏数据，理论上不存在
                        continue;
                    }
                    if (TextUtil.isEmpty(bean.getDateBean().get(i).getDate())) { //脏数据，理论上不存在
                        bean.getDateBean().get(i).setDate(date);
                        bean.getDateBean().get(i).setNum(1);
                    } else {
                        if (bean.getDateBean().get(i).getDate().equals(date)) { //为当前date增加num
                            bean.getDateBean().get(i).setNum(bean.getDateBean().get(i).getNum() + 1);
                        } else {
                            index++;
                        }
                    }
                }
                if (index == bean.getDateBean().size()) { //新增date
                    ClickNumDateBean dateBean = new ClickNumDateBean();
                    dateBean.setDate(date);
                    dateBean.setNum(1);
                    bean.getDateBean().add(dateBean);
                } else {
                    //do nothing
                }
            }
            //保存数据到redis中
            redisTemplate.opsForValue().set(name, bean);
            return bean;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ClickNumBean get(String name) {
        try {
            //从redis中获取数据
            return (ClickNumBean) redisTemplate.opsForValue().get(name);
        } catch (Exception e) {
            return null;
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
