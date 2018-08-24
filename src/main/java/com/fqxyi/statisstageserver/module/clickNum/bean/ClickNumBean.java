package com.fqxyi.statisstageserver.module.clickNum.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author ShenBF
 * @desc 统计每天点击次数的数据结构
 * @date 2018/8/21
 */
public class ClickNumBean implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 点击事件名
     */
    private String name;
    /**
     * 日期集合
     */
    private List<ClickNumDateBean> dateBean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClickNumDateBean> getDateBean() {
        return dateBean;
    }

    public void setDateBean(List<ClickNumDateBean> dateBean) {
        this.dateBean = dateBean;
    }

}
