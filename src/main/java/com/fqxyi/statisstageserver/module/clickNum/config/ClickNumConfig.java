package com.fqxyi.statisstageserver.module.clickNum.config;

import com.fqxyi.statisstageserver.module.clickNum.bean.ClickNumBean;
import com.fqxyi.statisstageserver.common.util.ObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ShenBF
 * @desc 配置针对ClickNumBean的RedisTemplate实例
 * @date 2018/8/21
 */
@Configuration
public class ClickNumConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, ClickNumBean> redisTemplate() {
        RedisTemplate<String, ClickNumBean> template = new RedisTemplate<String, ClickNumBean>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new ObjectSerializer());
        return template;
    }

}
