package com.fqxyi.statisstageserver.module.clickNum.service.impl;

import com.fqxyi.statisstageserver.common.exception.ExceptionConstant;
import com.fqxyi.statisstageserver.common.util.TextUtil;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumBean;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumFedBean;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumFedItemBean;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumItemBean;
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

    private final String KEY = "CLICKNUM";
    private final String KEY_FED = "CLICKNUM_FED";
    private final String KEY_DATE = "CLICKNUM_DATE";
    private final String KEY_NAME = "CLICKNUM_NAME";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int set(String name) {
        try {
            String date = sdf.format(new Date());
            if (TextUtil.isEmpty(date)) {
                return ExceptionConstant.CODE_ERR_EX;
            }
            saveStr(KEY_DATE, date);
            saveStr(KEY_NAME, name);
            saveData(name, date);
            saveFedData(name, date);
            return ExceptionConstant.CODE_OK;
        } catch (Exception e) {
            return ExceptionConstant.CODE_ERR_EX;
        }
    }

    private void saveFedData(String name, String date) {
        //从redis中获取数据
        ClickNumFedBean bean = (ClickNumFedBean) redisTemplate.opsForValue().get(KEY_FED);
        if (bean == null) {
            bean = new ClickNumFedBean();
            List<String> nameList = new ArrayList<>();
            nameList.add(name);
            bean.setName(nameList);
            List<String> dateList = new ArrayList<>();
            dateList.add(date);
            bean.setDate(dateList);
            List<Integer> numList = new ArrayList<>();
            numList.add(1);
            ClickNumFedItemBean itemBean = new ClickNumFedItemBean();
            itemBean.setName(name);
            itemBean.setNum(numList);
            List<ClickNumFedItemBean> itemBeanList = new ArrayList<>();
            itemBeanList.add(itemBean);
            bean.setItem(itemBeanList);
        } else {
            //保存name
            boolean existsName = false;
            List<String> nameList = bean.getName();
            for (String nameStr : nameList) {
                if (TextUtil.isEmpty(nameStr)) {
                    continue;
                }
                if (nameStr.equals(name)) {
                    existsName = true;
                    break;
                }
            }
            if (!existsName) { //name不存在
                nameList.add(name);
            }
            //保存date
            boolean existsDate = false;
            int dateIndex = -1;
            List<String> dateList = bean.getDate();
            for (String dateStr : dateList) {
                dateIndex++;
                if (TextUtil.isEmpty(dateStr)) {
                    continue;
                }
                if (dateStr.equals(date)) {
                    existsDate = true;
                    break;
                }
            }
            if (!existsDate) { //date不存在
                dateList.add(date);
                dateIndex = dateList.size() - 1;
            }
            //保存item
            boolean existsItemName = false;
            List<ClickNumFedItemBean> itemBeanList = bean.getItem();
            for (ClickNumFedItemBean itemBean : itemBeanList) {
                if (itemBean == null) {
                    continue;
                }
                if (itemBean.getName().equals(name)) { //name存在
                    existsItemName = true;
                    if (dateIndex + 1 > itemBean.getNum().size()) { //新date
                        itemBean.getNum().add(1);
                    } else { //旧date
                        itemBean.getNum().set(dateIndex, itemBean.getNum().get(dateIndex) + 1);
                    }
                    break;
                }
            }
            if (!existsItemName) { //name不存在
                List<Integer> numList = new ArrayList<>();
                for (String dateStr : dateList) {
                    numList.add(0);
                }
                numList.set(dateIndex, 1);
                ClickNumFedItemBean itemBean = new ClickNumFedItemBean();
                itemBean.setName(name);
                itemBean.setNum(numList);
                itemBeanList.add(itemBean);
            }
        }
        //保存数据到redis中
        redisTemplate.opsForValue().set(KEY_FED, bean);
    }

    private void saveData(String name, String date) {
        //从redis中获取数据
        List<ClickNumBean> list = (List<ClickNumBean>) redisTemplate.opsForValue().get(KEY);
        if (list == null || list.size() == 0) { //数据不存在
            ClickNumItemBean itemBean = new ClickNumItemBean();
            itemBean.setName(name);
            itemBean.setNum(1);
            List<ClickNumItemBean> itemBeanList = new ArrayList<>();
            itemBeanList.add(itemBean);
            ClickNumBean bean = new ClickNumBean();
            bean.setDate(date);
            bean.setItemBean(itemBeanList);
            list = new ArrayList<>();
            list.add(bean);
        } else {
            boolean hasDate = false;
            for (int i = 0; i < list.size(); i++) {
                ClickNumBean bean = list.get(i);
                if (bean == null || TextUtil.isEmpty(bean.getDate())) { //脏数据，理论上不存在
                    continue;
                }
                if (bean.getDate().equals(date)) { //date存在
                    hasDate = true;
                    List<ClickNumItemBean> itemBeanList = bean.getItemBean(); //为name事件计数
                    if (itemBeanList == null || itemBeanList.size() == 0) { //脏数据，理论上不存在
                        ClickNumItemBean itemBean = new ClickNumItemBean();
                        itemBean.setName(name);
                        itemBean.setNum(1);
                        itemBeanList = new ArrayList<>();
                        itemBeanList.add(itemBean);
                    } else {
                        boolean hasName = false;
                        for (int j = 0; j < itemBeanList.size(); j++) {
                            ClickNumItemBean itemBean = itemBeanList.get(j);
                            if (itemBean == null || TextUtil.isEmpty(itemBean.getName())) { //脏数据，理论上不存在
                                continue;
                            }
                            if (itemBean.getName().equals(name)) { //name存在
                                hasName = true;
                                itemBean.setNum(itemBean.getNum() + 1); //次数+1
                                break;
                            }
                        }
                        if (!hasName) { //name不存在
                            ClickNumItemBean newItemBean = new ClickNumItemBean();
                            newItemBean.setName(name);
                            newItemBean.setNum(1);
                            itemBeanList.add(newItemBean);
                        }
                    }
                    break;
                }
            }
            if (!hasDate) { //date不存在
                ClickNumItemBean newItemBean = new ClickNumItemBean();
                newItemBean.setName(name);
                newItemBean.setNum(1);
                List<ClickNumItemBean> newItemBeanList = new ArrayList<>();
                newItemBeanList.add(newItemBean);
                ClickNumBean newBean = new ClickNumBean();
                newBean.setDate(date);
                newBean.setItemBean(newItemBeanList);
                list.add(newBean);
            }
        }
        //保存数据到redis中
        redisTemplate.opsForValue().set(KEY, list);
    }

    private void saveStr(String key, String data) {
        //从redis中获取数据
        List<String> dataList = (List<String>) redisTemplate.opsForValue().get(key);
        if (dataList == null || dataList.size() == 0) {
            dataList = new ArrayList<>();
        }
        boolean hasData = false;
        for (String dataStr : dataList) {
            if (TextUtil.isEmpty(dataStr)) {
                continue;
            }
            if (dataStr.equals(data)) { //data已存在
                hasData = true;
                break;
            }
        }
        if (!hasData) { //data不存在
            dataList.add(data);
        }
        //保存数据到redis中
        redisTemplate.opsForValue().set(key, dataList);
    }

    @Override
    public ClickNumFedBean getFed() {
        try {
            //从redis中获取数据
            return (ClickNumFedBean) redisTemplate.opsForValue().get(KEY_FED);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ClickNumBean> getAll() {
        try {
            //从redis中获取数据
            return (List<ClickNumBean>) redisTemplate.opsForValue().get(KEY);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getAllDate() {
        try {
            //从redis中获取数据
            return (List<String>) redisTemplate.opsForValue().get(KEY_DATE);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getAllName() {
        try {
            //从redis中获取数据
            return (List<String>) redisTemplate.opsForValue().get(KEY_NAME);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int delAll() {
        try {
            //保存数据到redis中
            redisTemplate.opsForValue().set(KEY, null);
            redisTemplate.opsForValue().set(KEY_FED, null);
            redisTemplate.opsForValue().set(KEY_DATE, null);
            redisTemplate.opsForValue().set(KEY_NAME, null);
            return ExceptionConstant.CODE_OK;
        } catch (Exception e) {
            return ExceptionConstant.CODE_ERR_EX;
        }
    }

}
