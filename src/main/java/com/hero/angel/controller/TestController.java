package com.hero.angel.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "项目基本测试")
@RestController
public class TestController {


    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
