package com.fqxyi.statisstageserver.module.clickNum.bean;

import com.fqxyi.statisstageserver.common.exception.ExceptionConstant;

import java.util.List;

/**
 * @author ShenBF
 * @desc 用于Controller处理数据的数据结构
 * @date 2018/8/28
 */
public class ResultBean {

    /**
     * 错误类型，参考{@link ExceptionConstant}
     */
    private int type;
    /**
     * 返回给调用者的数据源
     */
    private List<ClickNumBean> data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ClickNumBean> getData() {
        return data;
    }

    public void setData(List<ClickNumBean> data) {
        this.data = data;
    }
}
