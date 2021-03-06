package com.fqxyi.statisstageserver.module.clickNum.controller;

import com.fqxyi.statisstageserver.common.bean.ResponseBean;
import com.fqxyi.statisstageserver.common.exception.ExceptionConstant;
import com.fqxyi.statisstageserver.common.util.GsonUtil;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumBean;
import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumFedBean;
import com.fqxyi.statisstageserver.module.clickNum.service.ClickNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        int result = clickNumService.set(name);
        //返回响应数据
        if (result == ExceptionConstant.CODE_ERR_EX) {
            responseBean.resultCode = ExceptionConstant.CODE_ERR_EX;
            responseBean.resultMessage = "服务异常";
        } else {
            responseBean.resultCode = ExceptionConstant.CODE_OK;
            responseBean.resultMessage = "数据设置成功";
        }
        return GsonUtil.GsonToString(responseBean);
    }

    @RequestMapping("/getFed")
    public String getFed() {
        ResponseBean responseBean = new ResponseBean();
        ClickNumFedBean result = clickNumService.getFed();
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

    @RequestMapping("/getAll")
    public String getAll() {
        ResponseBean responseBean = new ResponseBean();
        List<ClickNumBean> result = clickNumService.getAll();
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

    @RequestMapping("/getAllDate")
    public String getAllDate() {
        ResponseBean responseBean = new ResponseBean();
        List<String> result = clickNumService.getAllDate();
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

    @RequestMapping("/getAllName")
    public String getAllName() {
        ResponseBean responseBean = new ResponseBean();
        List<String> result = clickNumService.getAllName();
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

    @RequestMapping("/delAll")
    public String del() {
        ResponseBean responseBean = new ResponseBean();
        int result = clickNumService.delAll();
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
