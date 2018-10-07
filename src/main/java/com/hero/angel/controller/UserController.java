package com.hero.angel.controller;

import com.hero.angel.domain.TbUser;
import com.hero.angel.result.ResultBean;
import com.hero.angel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "用户操作接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("获得用户列表")
    @GetMapping
    public ResultBean getUsers() {
        List<TbUser> users = userService.getUsers();
        return ResultBean.ok(users);
    }

    @ApiOperation("通过Id获得用户")
    @GetMapping("/{userId}")
    public ResultBean getUserById(@PathVariable Long userId) {
        TbUser user = userService.getUserById(userId);
        return ResultBean.ok(user);
    }

    @ApiOperation("通过Id删除用户")
    @DeleteMapping("/{userId}")
    public ResultBean deleteUserById(@PathVariable Long userId) {
        int i = userService.deleteUserById(userId);
        return ResultBean.ok();
    }

}
