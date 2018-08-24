package com.fqxyi.statisstageserver.common.bean;

/**
 * @author ShenBF
 * @desc 响应数据结构
 * @date 2018/8/24
 */
public class ResponseBean<T> {

    public int resultCode;
    public String resultMessage;
    public T data;

}
