package com.hero.angel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.hero.angel.mapper"})
@SpringBootApplication
public class AngelManagerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AngelManagerServerApplication.class, args);
    }
}
