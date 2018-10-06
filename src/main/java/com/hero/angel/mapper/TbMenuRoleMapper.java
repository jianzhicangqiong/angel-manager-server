package com.hero.angel.mapper;

import com.hero.angel.domain.TbMenuRole;
import com.hero.angel.domain.TbMenuRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbMenuRoleMapper {
    long countByExample(TbMenuRoleExample example);

    int deleteByExample(TbMenuRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbMenuRole record);

    int insertSelective(TbMenuRole record);

    List<TbMenuRole> selectByExample(TbMenuRoleExample example);

    TbMenuRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbMenuRole record, @Param("example") TbMenuRoleExample example);

    int updateByExample(@Param("record") TbMenuRole record, @Param("example") TbMenuRoleExample example);

    int updateByPrimaryKeySelective(TbMenuRole record);

    int updateByPrimaryKey(TbMenuRole record);
}