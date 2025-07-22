package com.minjer.securitydemo.security;

import com.minjer.securitydemo.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Minjer
 * @since 2025-07-21
 * * JWT认证过滤器
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 去掉 "Bearer "
        String token = authHeader.substring(7);

        try {
            // 验证JWT令牌
            if (JwtUtil.validateToken(token)) {
                // 从JWT中获取用户名和权限
                String username = JwtUtil.getUsernameFromToken(token);
                List<String> authorities = JwtUtil.getRolesFromToken(token);

                // 转换权限字符串为 GrantedAuthority
                List<GrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // 构建已认证的 Authentication 对象
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            // 如果JWT验证失败，清除SecurityContext
            SecurityContextHolder.clearContext();
            // 可以在这里记录日志或处理异常
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
            return;
        }

        // 继续过滤器链
        filterChain.doFilter(request, response);
    }
}
