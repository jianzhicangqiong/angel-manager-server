package com.hero.angel.filter;

import com.hero.angel.domain.TbUser;
import com.hero.angel.service.UserService;
import com.hero.angel.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 令牌校验
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 在此方法中校验客户端的 Authentication
     * 如果合法，就把Authentication中信息封装到Authentication对象中
     * 最后使用 SecurityContextHolder.getContext().setAuthentication(authentication)，
     * 改变或删除当前已经验证的权限
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authentication");
        if (token != null && token.startsWith("Bearer ")) {
            // token去Bearer
            token.replace("Bearer ", "");
            // 获得用户名
            String username = jwtTokenUtil.getUsernameFromToken(token);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 查询数据库，检验信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 核数据库中信息对比
                if(jwtTokenUtil.validateToken(token, userDetails)) {
                    // 核实无误，返回token
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
