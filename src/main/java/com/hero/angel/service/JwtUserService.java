package com.hero.angel.service;

import com.hero.angel.domain.JwtUser;

public interface JwtUserService {

    /**
     * 用户登录
     * @param user
     * @return
     */
    String login(JwtUser user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    String register(JwtUser user);

    /**
     * 刷新密钥
     * @param token 原密钥
     * @return 新密钥
     */
    String refreshToken(String token);
}
