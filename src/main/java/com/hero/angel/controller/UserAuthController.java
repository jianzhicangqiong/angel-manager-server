package com.hero.angel.controller;

import com.hero.angel.domain.JwtUser;
import com.hero.angel.domain.TbUser;
import com.hero.angel.result.ResultBean;
import com.hero.angel.service.JwtUserService;
import com.hero.angel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "涉及用户权限的接口")
@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Resource
    private JwtUserService jwtUserService;

    @ApiOperation("用户登录")
    @PostMapping("/token")
    public ResultBean authLogin(@RequestBody JwtUser user) {
        String token = jwtUserService.login(user);
        if(token == null) {
            return ResultBean.build("登录失败");
        }
        return ResultBean.ok(token);
    }

    @ApiOperation("添加用户，用户注册")
    @PostMapping("/user")
    public ResultBean authRegister(@RequestBody JwtUser user) {
        int i = jwtUserService.register(user);
        return ResultBean.ok(i);
    }

}
