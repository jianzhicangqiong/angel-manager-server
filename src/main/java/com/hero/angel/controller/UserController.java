package com.hero.angel.controller;

import com.hero.angel.domain.TbUser;
import com.hero.angel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public List<TbUser> getUsers() {
        return userService.getUsers();
    }

    @ApiOperation("通过Id获得用户")
    @GetMapping("/{userId}")
    public TbUser getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @ApiOperation("通过Id删除用户")
    @DeleteMapping("/{userId}")
    public int deleteUserById(@PathVariable Long userId) {
        return userService.deleteUserById(userId);
    }

}
