package com.fqxyi.statisstageserver.module.clickNum.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author ShenBF
 * @desc 返回给调用者的数据结构
 * @date 2018/8/21
 */
public class ClickNumBean implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 日期
     */
    private String date;
    /**
     * 事件集合
     */
    private List<ClickNumItemBean> itemBean;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ClickNumItemBean> getItemBean() {
        return itemBean;
    }

    public void setItemBean(List<ClickNumItemBean> itemBean) {
        this.itemBean = itemBean;
    }
}
