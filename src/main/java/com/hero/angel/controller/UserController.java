package com.hero.angel.controller;

import com.github.pagehelper.PageInfo;
import com.hero.angel.domain.TbUser;
import com.hero.angel.result.PageBean;
import com.hero.angel.result.ResultBean;
import com.hero.angel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Api(tags = "用户操作接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private Logger logger;

    @Resource
    private UserService userService;

    @ApiOperation("导出用户信息为Excel")
    @GetMapping("/excel")
    public void userToExcel(Integer currentPage, Integer pageSize, HttpServletResponse response) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户信息表");

        List<TbUser> users = userService.getUsers(currentPage, pageSize).getList();

        String fileName = "user-" + currentPage + "-" + pageSize + ".xls";

        // 新增数据行，并设置单元格数据
        int rowNum = 1;
        String[] headers = {"用户Id", "用户名称", "手机", "邮箱", "昵称", "姓名", "最后登录时间"};

        //
        HSSFRow hssfRow = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = hssfRow.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 在表中放置相应的列
        for (TbUser user : users) {
            HSSFRow dataRow = sheet.createRow(rowNum);
            dataRow.createCell(0).setCellValue(user.getUserId());
            dataRow.createCell(1).setCellValue(user.getUsername());
            dataRow.createCell(2).setCellValue(user.getPhone());
            dataRow.createCell(3).setCellValue(user.getEmail());
            dataRow.createCell(4).setCellValue(user.getNickName());
            dataRow.createCell(5).setCellValue(user.getRealName());
            dataRow.createCell(6).setCellValue(user.getLastLoginTime());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition","attachment;filename="+fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @ApiOperation("获得用户列表")
    @GetMapping
    public ResultBean getUsers(Integer currentPage, Integer pageSize) {

        PageBean pageBean = new PageBean(currentPage, pageSize);

        try {
            PageInfo pageInfo = userService.getUsers(currentPage, pageSize);
            // 封装
            pageBean.setTotal(pageInfo.getTotal());
            pageBean.setList(pageInfo.getList());
            return ResultBean.ok(pageBean);
        } catch (Exception e) {
            return ResultBean.build("查询用户失败");
        }
    }

    @ApiOperation("通过Id获得用户")
    @GetMapping("/{userId}")
    public ResultBean getUserById(@PathVariable Long userId) {
        try {
            TbUser user = userService.getUserById(userId);
            if (user == null) {
                return ResultBean.build("此用户不存在！");
            }
            return ResultBean.ok(user);
        } catch (Exception e) {
            return ResultBean.build("查询失败！");
        }
    }

    @ApiOperation("通过Id删除用户")
    @DeleteMapping("/{userId}")
    public ResultBean deleteUserById(@PathVariable Long userId) {
        try {
            int i = userService.deleteUserById(userId);
            if (i == 0) {
                return ResultBean.build("无此用户");
            }
            return ResultBean.ok();
        } catch (Exception e) {
            return ResultBean.build("删除用户失败");
        }
    }
}
