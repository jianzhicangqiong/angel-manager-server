package com.hero.angel.controller;

import com.hero.angel.domain.TbUser;
import com.hero.angel.result.ResultBean;
import com.hero.angel.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Resource
    private UserService userService;

    @PostMapping("/token")
    public ResultBean authLogin(@RequestBody TbUser user) {
        TbUser checkUser = userService.checkUser(user);
        if(checkUser == null) {
            return ResultBean.build("登录失败");
        }
        return ResultBean.ok(checkUser);
    }
}
