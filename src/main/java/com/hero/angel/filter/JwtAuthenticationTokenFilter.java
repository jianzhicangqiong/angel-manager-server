package com.hero.angel.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 令牌校验
 */

public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    private static final String SECRET = "I am a student";

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    /**
     * 在此方法中校验客户端的 Authentication
     * 如果合法，就把Authentication中信息封装到Authentication对象中
     * 最后使用 SecurityContextHolder.getContext().setAuthentication(authentication)，
     *  改变或删除当前已经验证的 pricipal
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authentication = request.getHeader("Authentication");
        if (authentication == null || !authentication.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //
        UsernamePasswordAuthenticationToken authenticationToken =
                getAuthencation(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private UsernamePasswordAuthenticationToken getAuthencation(String authentication) {

        Claims claims = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(authentication.replace("Bearer ", ""))
                .getBody();

        // 得到用户名
        String username = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
    }
}
