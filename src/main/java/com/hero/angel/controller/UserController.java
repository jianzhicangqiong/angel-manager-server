package com.hero.angel.controller;

import com.hero.angel.domain.TbUser;
import com.hero.angel.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping
    public List<TbUser> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public TbUser getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public int deleteUserById(@PathVariable Long userId) {
        return userService.deleteUserById(userId);
    }

}
