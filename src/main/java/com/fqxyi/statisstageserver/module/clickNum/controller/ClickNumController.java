package com.fqxyi.statisstageserver.module.clickNum.controller;

import com.fqxyi.statisstageserver.common.exception.ExceptionConstant;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumBean;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ShenBF
 * @desc 点击次数控制器
 * @date 2018/8/21
 */
@RestController
public class ClickNumController {

    /**
     * 缓存点击次数
     */
    private int num = 0;

    @Autowired
    private RedisTemplate<String, ClickNumBean> redisTemplate;

    @RequestMapping("/set")
    public String set(@RequestParam("name") String name) {
        JSONObject jsonObject = new JSONObject();
        if (name == null || name.length() == 0) {
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_DATA);
            jsonObject.put("resultMessage", "name不能为空");
            return jsonObject.toString();
        }
        try {
            //从redis中获取数据
            ClickNumBean bean = redisTemplate.opsForValue().get(name);
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
            //返回响应数据
            jsonObject.put("resultCode", ExceptionConstant.CODE_OK);
            jsonObject.put("resultMessage", "统计成功");
        } catch (Exception e) {
            //返回响应数据
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_EX);
            jsonObject.put("resultMessage", "服务异常");
        }
        return jsonObject.toString();
    }

    @RequestMapping("/get")
    public String get(@RequestParam("name") String name) {
        JSONObject jsonObject = new JSONObject();
        if (name == null || name.length() == 0) {
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_DATA);
            jsonObject.put("resultMessage", "name不能为空");
            return jsonObject.toString();
        }
        try {
            //从redis中获取数据
            ClickNumBean bean = redisTemplate.opsForValue().get(name);
            if (bean == null) {
                //返回响应数据
                jsonObject.put("resultCode", ExceptionConstant.CODE_OK);
                jsonObject.put("resultMessage", "name " + name + " 不存在");
            } else {
                //返回响应数据
                jsonObject.put("resultCode", ExceptionConstant.CODE_OK);
                jsonObject.put("resultMessage", "name " + name + " 点击了 " + bean.getNum() + " 次");
            }
        } catch (Exception e) {
            //返回响应数据
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_EX);
            jsonObject.put("resultMessage", "服务异常");
        }
        return jsonObject.toString();
    }

}
