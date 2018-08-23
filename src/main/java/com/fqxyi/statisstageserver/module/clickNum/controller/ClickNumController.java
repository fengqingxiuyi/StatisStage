package com.fqxyi.statisstageserver.module.clickNum.controller;

import com.fqxyi.statisstageserver.common.exception.ExceptionConstant;
import com.fqxyi.statisstageserver.module.clickNum.service.ClickNumService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShenBF
 * @desc 点击次数控制器
 * @date 2018/8/21
 */
@RestController
public class ClickNumController {

    @Autowired
    private ClickNumService clickNumService;

    @RequestMapping("/set")
    public String set(@RequestParam("name") String name) {
        JSONObject jsonObject = new JSONObject();
        if (name == null || name.length() == 0) {
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_DATA);
            jsonObject.put("resultMessage", "name不能为空");
            return jsonObject.toString();
        }
        int result = clickNumService.set(name);
        //返回响应数据
        if (result == ExceptionConstant.CODE_ERR_EX) {
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_EX);
            jsonObject.put("resultMessage", "服务异常");
        } else {
            jsonObject.put("resultCode", ExceptionConstant.CODE_OK);
            jsonObject.put("resultMessage", "统计成功");
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
        int result = clickNumService.get(name);
        //返回响应数据
        if (result == ExceptionConstant.CODE_ERR_EX) {
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_EX);
            jsonObject.put("resultMessage", "服务异常");
        } else if (result == ExceptionConstant.CODE_ERR_DATA) {
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_DATA);
            jsonObject.put("resultMessage", "name " + name + " 不存在");
        } else {
            jsonObject.put("resultCode", ExceptionConstant.CODE_OK);
            jsonObject.put("resultMessage", "name " + name + " 点击了 " + result + " 次");
        }
        return jsonObject.toString();
    }

    @RequestMapping("/del")
    public String del(@RequestParam("name") String name) {
        JSONObject jsonObject = new JSONObject();
        if (name == null || name.length() == 0) {
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_DATA);
            jsonObject.put("resultMessage", "name不能为空");
            return jsonObject.toString();
        }
        int result = clickNumService.del(name);
        //返回响应数据
        if (result == ExceptionConstant.CODE_ERR_EX) {
            jsonObject.put("resultCode", ExceptionConstant.CODE_ERR_EX);
            jsonObject.put("resultMessage", "服务异常");
        } else {
            jsonObject.put("resultCode", ExceptionConstant.CODE_OK);
            jsonObject.put("resultMessage", "数据删除成功");
        }
        return jsonObject.toString();
    }

}
