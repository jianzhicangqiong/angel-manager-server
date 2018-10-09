package com.hero.angel.service;

import com.github.pagehelper.PageInfo;
import com.hero.angel.domain.TbUser;

/**
 * 用户操作接口规范
 */
public interface UserService {

    /**
     * 添加用户
     * @param user
     * @return
     */
    int insertUser(TbUser user);

    /**
     * 获得用户列表
     * @return
     */
    PageInfo<TbUser> getUsers(Integer currentPage, Integer pageSize);

    /**
     * 通过Id获得用户
     * @param userId
     * @return
     */
    TbUser getUserById(Long userId);

    /**
     * 通过用户Id删除用户
     * @param userId
     * @return 影响的行数
     */
    int deleteUserById(Long userId);
}
