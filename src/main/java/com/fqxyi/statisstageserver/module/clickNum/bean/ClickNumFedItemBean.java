package com.fqxyi.statisstageserver.module.clickNum.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author ShenBF
 * @desc 前端需要的数据结构
 * @date 2018/8/28
 */
public class ClickNumFedItemBean implements Serializable {

    private static final long serialVersionUID = -1L;

    private String name;

    private List<Integer> num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getNum() {
        return num;
    }

    public void setNum(List<Integer> num) {
        this.num = num;
    }
}
