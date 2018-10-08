package com.hero.angel.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String SECRET = "I am a student";

    private AuthenticationManager authenticationManager;


    /**
     * 接受并解析用户登录信息  /login
     * 为已经验证的用户返回一个已经填充的身份验证令牌，表示成功的身份验证
     * 返回null， 表明身份验证仍在进行中，在返回之前，事项该执行完成过程所需要的额外工作
     * 如果身份认证失败，就抛异常
     * @param request
     * @param response
     * @return 身份验证的用户令牌，如果身份验证不完整，则为null。
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 得到用户信息，并封装到Authentication中，供自定义用户组件使用
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(StringUtils.isBlank(username)){
            username = "";
        }

        if(StringUtils.isBlank(password)) {
            password = "";
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password, authorities);

        //UsernamePasswordAuthenticationToken 是 Authentication 的实现类
        return authenticationToken;
    }

    /**
     * 登录成功之后，此方法会被调用，在请求头中添加 Authentication 返回给客户端
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                // 设置有效期两个小时
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*2))
                // 采用加密算法
                .signWith(SignatureAlgorithm.ES512, SECRET)
                .compact();
        response.addHeader("Authentication", "Bearer " + token);
    }
}
