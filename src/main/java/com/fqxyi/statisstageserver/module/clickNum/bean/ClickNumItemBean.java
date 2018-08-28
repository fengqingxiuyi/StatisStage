package com.fqxyi.statisstageserver.module.clickNum.bean;

import java.io.Serializable;

/**
 * @author ShenBF
 * @desc 返回给调用者的数据结构
 * @date 2018/8/24
 */
public class ClickNumItemBean implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 事件名
     */
    private String name;

    /**
     * 该事件的点击次数
     */
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
