package com.hero.angel.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {

    // 添加日志
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }

}