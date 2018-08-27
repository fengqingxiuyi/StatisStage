package com.fqxyi.statisstageserver.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ShenBF
 * @desc 全局跨域配置
 * @date 2018/7/25
 */
/*
@Configuration : Spring Boot提倡基于Java的配置。尽管可以使用一个XML源来调用SpringApplication.run()，但官方建议使用@Configuration类作为主要源。
一般定义main方法的类也是主要@Configuration的一个很好候选。不需要将所有的@Configuration放进一个单独的类。@Import注解可以用来导入其他配置类。
另外也可以使用@ComponentScan注解自动收集所有的Spring组件，包括@Configuration类。
如果需要使用基于XML的配置，官方建议仍旧从一个@Configuration类开始。可以使用附加的@ImportResource注解加载XML配置文件。
@Configuration注解该类，等价与XML中配置beans；用@Bean标注方法等价于XML中配置bean。
 */
@Configuration
public class CORSConfig {

    /**
     * 相当于XML中的,放在方法的上面，而不是类，意思是产生一个bean,并交给spring管理。
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins(
                                "http://vue.fqxyi.com",
                                "http://localhost:8080",
                                "http://localhost:8081",
                                "http://localhost:8082",
                                "http://localhost:8083",
                                "http://localhost:8084");
            }
        };
    }

}
