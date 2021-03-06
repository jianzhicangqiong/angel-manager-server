package com.hero.angel.service.impl;

import com.hero.angel.config.InitDataConfig;
import com.hero.angel.domain.JwtUser;
import com.hero.angel.domain.TbRole;
import com.hero.angel.domain.TbUser;
import com.hero.angel.domain.TbUserRole;
import com.hero.angel.mapper.TbUserMapper;
import com.hero.angel.mapper.TbUserRoleMapper;
import com.hero.angel.service.JwtUserService;
import com.hero.angel.service.RoleService;
import com.hero.angel.service.UserService;
import com.hero.angel.util.JwtTokenUtil;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserServiceImpl implements JwtUserService, UserDetailsService {

    @Resource
    private InitDataConfig initDataConfig;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private TbUserMapper userMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private TbUserRoleMapper userRoleMapper;

    /**
     * 用户权限认证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TbUser user = userService.selectUserByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("用户不存在！！！");
        }

        // 获得该用户的权限
        List<TbRole> roles = roleService.getRolesByUserId(user.getUserId());
        // 权限 ,这种方式构建，一定要使用 "ROLE_xxx"格式
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(TbRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        //authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new JwtUser(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public String login(JwtUser user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication =  authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        return "Bearer " + jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    @Transactional
    public int register(JwtUser user) {

        // 通过用户名查找
        TbUser tbUser = userService.selectUserByUsername(user.getUsername());

        if(tbUser != null) {
            return 0;
        }

        // 密码加密
        String encodingPassword = bCryptPasswordEncoder.encode(user.getPassword());
        TbUser insertUser = new TbUser();
        insertUser.setUsername(user.getUsername());
        insertUser.setPassword(encodingPassword);
        int i = userMapper.insertSelective(insertUser);

        // 给用户添加默认角色 Tb_User
        TbUserRole userRole = new TbUserRole();
        userRole.setUserId(insertUser.getUserId());
        userRole.setRoleId(initDataConfig.getInitRoleId());
        userRoleMapper.insertSelective(userRole);

        return i;
    }

    @Override
    public String refreshToken(String token) {
        return null;
    }
}
