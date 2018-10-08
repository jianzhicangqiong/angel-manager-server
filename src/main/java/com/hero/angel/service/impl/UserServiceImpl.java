package com.hero.angel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hero.angel.domain.JwtUser;
import com.hero.angel.domain.TbUser;
import com.hero.angel.domain.TbUserExample;
import com.hero.angel.mapper.TbUserMapper;
import com.hero.angel.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Resource
    private TbUserMapper userMapper;

    /**
     * 用户权限认证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria userExampleCriteria = userExample.createCriteria();
        userExampleCriteria.andUsernameEqualTo(username);

        List<TbUser> users = userMapper.selectByExample(userExample);
        if(users.size() != 1) {
            throw new UsernameNotFoundException("用户不存在");
        }
        TbUser user = users.get(0);

        // 权限
        // TODO
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new JwtUser(user.getUsername(), user.getPassword(), authorities);
    }

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
