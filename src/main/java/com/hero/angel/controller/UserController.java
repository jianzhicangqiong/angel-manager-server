package com.hero.angel.controller;

import com.github.pagehelper.PageInfo;
import com.hero.angel.domain.TbUser;
import com.hero.angel.result.PageBean;
import com.hero.angel.result.ResultBean;
import com.hero.angel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Api(tags = "用户操作接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private Logger logger;

    @Resource
    private UserService userService;

    @ApiOperation("获得用户列表")
    @RequestMapping
    public ResultBean getUsers(Integer currentPage, Integer pageSize) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        logger.info(pageBean.getCurrentPage() + "\t" + pageBean.getPageSize());
        try {
            PageInfo pageInfo = userService.getUsers(currentPage, pageSize);
            // 封装
            pageBean.setTotal(pageInfo.getTotal());
            pageBean.setList(pageInfo.getList());

            return ResultBean.ok(pageBean);
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
