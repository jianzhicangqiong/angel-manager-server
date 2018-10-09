package com.hero.angel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hero.angel.domain.TbUser;
import com.hero.angel.domain.TbUserExample;
import com.hero.angel.mapper.TbUserMapper;
import com.hero.angel.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TbUserMapper userMapper;

    @Override
    public int insertUser(TbUser user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public PageInfo<TbUser> getUsers(Integer currentPage, Integer pageSize) {

        // 添加分页
        PageHelper.startPage(currentPage, pageSize);
        List<TbUser> users = userMapper.selectByExample(new TbUserExample());

        // 获得分页的详细信息
        PageInfo<TbUser> pageInfo = new PageInfo<TbUser>(users);

        return pageInfo;
    }

    @Override
    public TbUser getUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int deleteUserById(Long userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

}
