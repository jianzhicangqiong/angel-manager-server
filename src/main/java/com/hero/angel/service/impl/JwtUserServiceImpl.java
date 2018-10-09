package com.hero.angel.service.impl;

import com.hero.angel.domain.JwtUser;
import com.hero.angel.domain.TbUser;
import com.hero.angel.domain.TbUserExample;
import com.hero.angel.mapper.TbUserMapper;
import com.hero.angel.service.JwtUserService;
import com.hero.angel.service.UserService;
import com.hero.angel.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtUserServiceImpl implements JwtUserService, UserDetailsService {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserService userService;

    @Resource
    private TbUserMapper userMapper;

    /**
     * 用户权限认证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria userExampleCriteria = userExample.createCriteria();
        userExampleCriteria.andUsernameEqualTo(username);

        List<TbUser> users = userMapper.selectByExample(userExample);
        if(users.size() != 1) {
            throw new UsernameNotFoundException("用户不存在");
        }
        TbUser user = users.get(0);

        // 权限
        // TODO
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        // authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new JwtUser(user.getUsername(), user.getPassword(), authorities);
    }

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
    public int register(JwtUser user) {
        String encodingPassword = bCryptPasswordEncoder.encode(user.getPassword());

        //
        TbUser insertUser = new TbUser();
        insertUser.setUsername(user.getUsername());
        insertUser.setPassword(encodingPassword);
        int i = userService.insertUser(insertUser);
        return i;
    }

    @Override
    public String refreshToken(String token) {
        return null;
    }
}
