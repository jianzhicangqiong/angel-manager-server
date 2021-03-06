package com.hero.angel.config;

import com.hero.angel.filter.JwtAuthenticationTokenFilter;
import com.hero.angel.handler.RestAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration  // 配置类注解
@EnableWebSecurity  // 开启SpringSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;

    // 解决AuthenticationManager 无法注入
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 身份认证组件
        auth.userDetailsService(this.userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * 拦截规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 允许如下请求
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "*.js",
                        "/webjars/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                // 其他所有请求需要认证
//                .anyRequest().hasAuthority("ROLE_ADMIN").and()
                .anyRequest().permitAll().and()
                .headers().cacheControl().and().and()
                // 验证登录
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 验证token出错
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler);
    }
}
