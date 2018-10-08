package com.hero.angel.service.impl;

import com.hero.angel.domain.JwtUser;
import com.hero.angel.service.JwtUserService;
import com.hero.angel.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JwtUserServiceImpl implements JwtUserService {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public String login(JwtUser user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication =  authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return "Bearer " + jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public String register(JwtUser user) {
        return null;
    }

    @Override
    public String refreshToken(String token) {
        return null;
    }
}
