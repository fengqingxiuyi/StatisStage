package com.fqxyi.statisstageserver.module.clickNum.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author ShenBF
 * @desc 前端需要的数据结构
 * @date 2018/8/28
 */
public class ClickNumFedBean implements Serializable {

    private static final long serialVersionUID = -1L;

    private List<String> name;

    private List<String> date;

    private List<ClickNumFedItemBean> item;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<ClickNumFedItemBean> getItem() {
        return item;
    }

    public void setItem(List<ClickNumFedItemBean> item) {
        this.item = item;
    }
}
