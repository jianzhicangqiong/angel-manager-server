package com.hero.angel.service;

import com.hero.angel.domain.TbRole;

import java.util.List;

/**
 * 用户角色接口
 */
public interface RoleService {

    /**
     * 通过用户获得角色集合
     * @param userId 用户Id
     * @return 角色集合
     */
    List<TbRole> getRolesByUserId(Long userId);

    /**
     * 获得子角色
     * @param parentId 父角色Id
     * @return
     */
    List<TbRole> getRolesByParent(Long parentId);

}
