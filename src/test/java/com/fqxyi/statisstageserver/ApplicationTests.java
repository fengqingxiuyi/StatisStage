package com.fqxyi.statisstageserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

//    @Autowired
//    //自动导入依赖的bean。byType方式。把配置好的Bean拿来用，完成属性、方法的组装，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。当加上（required=false）时，就算找不到bean也不报错。
//    private RedisTemplate<String, ClickNumBean> redisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {

        ValueOperations operations = redisTemplate.opsForValue();
        operations.set("springboot", "这是一个springboot集成redis的测试数据，字符串操作。");
        System.out.println(operations.get("springboot"));

//        // 保存对象
//        ClickNumBean bean = new ClickNumBean();
//        bean.setDate("2018-08-21");
//        bean.setName("点击了Button");
//        bean.setNum(10);
//        redisTemplate.opsForValue().set(bean.getName(), bean);
//        bean = new ClickNumBean();
//        bean.setDate("2018-08-22");
//        bean.setName("点击了TextView");
//        bean.setNum(5);
//        redisTemplate.opsForValue().set(bean.getName(), bean);
//        //
//        Assert.assertEquals(10, redisTemplate.opsForValue().get("点击了Button").getNum());
//        Assert.assertEquals(5, redisTemplate.opsForValue().get("点击了TextView").getNum());
    }

}
