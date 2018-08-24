package com.fqxyi.statisstageserver.module.clickNum.bean;

import java.io.Serializable;

/**
 * @author ShenBF
 * @desc 统计每天点击次数的数据结构
 * @date 2018/8/24
 */
public class ClickNumDateBean implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 日期
     */
    private String date;

    /**
     * 每一天的点击次数
     */
    private int num;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
