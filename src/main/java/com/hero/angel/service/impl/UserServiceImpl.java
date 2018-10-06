package com.hero.angel.service.impl;

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
    public List<TbUser> getUsers() {
        return userMapper.selectByExample(new TbUserExample());
    }

    @Override
    public TbUser getUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
