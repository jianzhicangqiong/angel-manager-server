package com.hero.angel.service;

import com.hero.angel.domain.TbUser;

import java.util.List;

/**
 * 用户操作接口规范
 */
public interface UserService {

    /**
     * 获得用户列表
     * @return
     */
    List<TbUser> getUsers();

    /**
     * 通过Id获得用户
     * @param userId
     * @return
     */
    TbUser getUserById(Long userId);
}
