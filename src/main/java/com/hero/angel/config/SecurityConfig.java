package com.hero.angel.config;

import com.hero.angel.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import javax.servlet.Filter;

@Configuration  // 配置类注解
@EnableWebSecurity  // 开启SpringSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 启用全局方法安全性
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserService userService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义身份认证组件 TODO
        auth.authenticationProvider(null);
    }

    /**
     * 拦截规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                // 允许如下请求
                .antMatchers("/hello").permitAll()
                // 其他所有请求需要认证
                .anyRequest().authenticated().and()
                // 验证登录 TODO
                .addFilterBefore(null, Filter.class)
                // 验证token TODO
                .addFilterBefore(null,Filter.class);
    }
}
