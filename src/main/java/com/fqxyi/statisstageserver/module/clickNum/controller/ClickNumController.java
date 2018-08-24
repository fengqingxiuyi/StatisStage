package com.fqxyi.statisstageserver.module.clickNum.controller;

import com.fqxyi.statisstageserver.common.bean.ResponseBean;
import com.fqxyi.statisstageserver.common.exception.ExceptionConstant;
import com.fqxyi.statisstageserver.common.util.GsonUtil;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumBean;
import com.fqxyi.statisstageserver.module.clickNum.service.ClickNumService;
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
        ResponseBean responseBean = new ResponseBean();
        if (name == null || name.length() == 0) {
            responseBean.resultCode = ExceptionConstant.CODE_ERR_DATA;
            responseBean.resultMessage = "name不能为空";
            return GsonUtil.GsonToString(responseBean);
        }
        ClickNumBean result = clickNumService.set(name);
        //返回响应数据
        if (result == null) {
            responseBean.resultCode = ExceptionConstant.CODE_ERR_EX;
            responseBean.resultMessage = "服务异常";
        } else {
            responseBean.resultCode = ExceptionConstant.CODE_OK;
            responseBean.resultMessage = "数据设置成功";
        }
        return GsonUtil.GsonToString(responseBean);
    }

    @RequestMapping("/get")
    public String get(@RequestParam("name") String name) {
        ResponseBean responseBean = new ResponseBean();
        if (name == null || name.length() == 0) {
            responseBean.resultCode = ExceptionConstant.CODE_ERR_DATA;
            responseBean.resultMessage = "name不能为空";
            return GsonUtil.GsonToString(responseBean);
        }
        ClickNumBean result = clickNumService.get(name);
        //返回响应数据
        if (result == null) {
            responseBean.resultCode = ExceptionConstant.CODE_ERR_EX;
            responseBean.resultMessage = "服务异常";
        } else {
            responseBean.resultCode = ExceptionConstant.CODE_OK;
            responseBean.resultMessage = "数据获取成功";
            responseBean.data = result;
        }
        return GsonUtil.GsonToString(responseBean);
    }

    @RequestMapping("/del")
    public String del(@RequestParam("name") String name) {
        ResponseBean responseBean = new ResponseBean();
        if (name == null || name.length() == 0) {
            responseBean.resultCode = ExceptionConstant.CODE_ERR_DATA;
            responseBean.resultMessage = "name不能为空";
            return GsonUtil.GsonToString(responseBean);
        }
        int result = clickNumService.del(name);
        //返回响应数据
        if (result == ExceptionConstant.CODE_ERR_EX) {
            responseBean.resultCode = ExceptionConstant.CODE_ERR_EX;
            responseBean.resultMessage = "服务异常";
        } else {
            responseBean.resultCode = ExceptionConstant.CODE_OK;
            responseBean.resultMessage = "数据删除成功";
        }
        return GsonUtil.GsonToString(responseBean);
    }

}
