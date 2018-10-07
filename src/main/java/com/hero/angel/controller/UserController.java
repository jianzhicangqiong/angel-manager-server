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
        try {
            List<TbUser> users = userService.getUsers();
            return ResultBean.ok(users);
        } catch (Exception e) {
            return ResultBean.build("查询用户失败");
        }
    }

    @ApiOperation("通过Id获得用户")
    @GetMapping("/{userId}")
    public ResultBean getUserById(@PathVariable Long userId) {
        try {
            TbUser user = userService.getUserById(userId);
            if (user == null) {
                return ResultBean.build("此用户不存在！");
            }
            return ResultBean.ok(user);
        } catch (Exception e) {
            return ResultBean.build("查询失败！");
        }
    }

    @ApiOperation("通过Id删除用户")
    @DeleteMapping("/{userId}")
    public ResultBean deleteUserById(@PathVariable Long userId) {
        try {
            int i = userService.deleteUserById(userId);
            if(i == 0) {
                return ResultBean.build("无此用户");
            }
            return ResultBean.ok();
        } catch (Exception e) {
            return ResultBean.build("删除用户失败");
        }
    }
}
