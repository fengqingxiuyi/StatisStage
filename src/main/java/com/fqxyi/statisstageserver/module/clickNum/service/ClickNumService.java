package com.fqxyi.statisstageserver.module.clickNum.service;

import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author ShenBF
 * @desc 点击次数接口
 * @date 2018/8/21
 */
public interface ClickNumService {

    /**
     * CachePut：表明Spring应该将方法的返回值放到缓存中，在方法的调用前并不会检查缓存，方法始终都会被调用。
     * CacheEvict：配置于函数上，通常用在删除方法上，用来从缓存中移除相应数据。
     * Cacheable：声明Spring在调用方法之前，首先应该在缓存中查找方法的返回值。如果这个值能够找到，就会返回存储的值，否则的话，这个方法就会被调用，返回值会放在缓存之中。
     *
     * value、cacheNames：两个等同的参数（cacheNames为Spring4新增，作为value的别名），用于指定缓存存储的集合名。
     * key：缓存对象存储在Map集合中的key值，非必需，缺省按照函数的所有参数组合作为key值，若自己配置需使用SpEL表达式，比如：@Cacheable(key = "#name")
     * condition：缓存对象的条件，非必需，也需使用SpEL表达式，只有满足表达式条件的内容才会被缓存
     */

    @CachePut(cacheNames = "clickNum", key="#name")
    ClickNumBean set(String name);

    @Cacheable(cacheNames = "clickNum", key="#name")
    ClickNumBean get(String name);

    @CacheEvict(cacheNames = "clickNum", key="#name")
    int del(String name);

}
