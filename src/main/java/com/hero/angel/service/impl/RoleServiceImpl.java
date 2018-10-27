package com.hero.angel.service.impl;

import com.hero.angel.domain.TbRole;
import com.hero.angel.domain.TbRoleExample;
import com.hero.angel.domain.TbUserRole;
import com.hero.angel.domain.TbUserRoleExample;
import com.hero.angel.mapper.TbRoleMapper;
import com.hero.angel.mapper.TbUserRoleMapper;
import com.hero.angel.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private TbUserRoleMapper userRoleMapper;

    @Resource
    private TbRoleMapper roleMapper;


    @Override
    public List<TbRole> getRolesByUserId(Long userId) {

        List<TbUserRole> userRoles = this.getUserRolesByUserId(userId);

        // 获得userRole的id的集合
        List<Long> roleIds = new ArrayList<>(8);
        for(TbUserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId());
        }

        // 获得具体的角色集合
        TbRoleExample roleExample = new TbRoleExample();
        TbRoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andRoleIdIn(roleIds);

        List<TbRole> roles = roleMapper.selectByExample(roleExample);
        return roles;
    }

    @Override
    public List<TbRole> getRolesByParent(Long parentId) {
        TbRoleExample roleExample = new TbRoleExample();
        TbRoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        return roleMapper.selectByExample(roleExample);
    }

    protected List<TbUserRole> getUserRolesByUserId(Long userId) {
        TbUserRoleExample userRoleExample = new TbUserRoleExample();
        TbUserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(userId);

        List<TbUserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        return userRoles;
    }
}
