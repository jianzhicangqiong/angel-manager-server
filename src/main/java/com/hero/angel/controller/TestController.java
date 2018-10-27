package com.hero.angel.controller;

import com.hero.angel.domain.TbUser;
import com.hero.angel.result.ResultBean;
import com.hero.angel.service.UserService;
import com.hero.angel.util.StringUtil;
import io.swagger.annotations.Api;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Api(tags = "项目基本测试")
@RestController
public class TestController {

    @Resource
    private UserService userService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/auto/register")
    public ResultBean autoRegister() throws UnsupportedEncodingException {


        for (int i = 0; i < 20; i++) {
            TbUser user = new TbUser();

            String username = "";
            for (int k = 0; k < 4; k++) {
                username = username + (char) (Math.random() * 26 + 'A');
            }
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder.encode("123456"));

            String phone = "1811133";
            for (int k = 0; k < 4; k++) {
                phone = phone + (int) (Math.random() * 10);
            }
            user.setPhone(phone);
            user.setEmail(phone + "@163.com");
            user.setLastLoginTime(new Date());

            String lastLoginIp = "";
            for (int k = 1; k <= 15; k++) {
                if(k%4 == 0){
                    lastLoginIp = lastLoginIp + ".";
                }else{
                    lastLoginIp = lastLoginIp + (int)(Math.random() * 10);
                }
            }
            user.setLastLoginIp(lastLoginIp);

            user.setNickName(username+"@");

            user.setRealName(username);

            userService.insertUser(user);

        }

        return ResultBean.ok();
    }
}
