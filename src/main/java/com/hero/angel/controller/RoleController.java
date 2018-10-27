package com.hero.angel.controller;

import com.hero.angel.domain.TbRole;
import com.hero.angel.result.ResultBean;
import com.hero.angel.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.Removal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "角色处理接口")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation("通过父角色获得子角色")
    @GetMapping("/{parentId}")
    public ResultBean getRolesByParentId(@PathVariable Long parentId) {
        List<TbRole> roles = roleService.getRolesByParent(parentId);
        try {
            return ResultBean.ok(roles);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultBean.build("查询失败");
        }
    }
}
