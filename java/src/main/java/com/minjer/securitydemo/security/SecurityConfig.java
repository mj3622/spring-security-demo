package com.minjer.securitydemo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Minjer
 * @since 2025-07-21
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DynamicAuthorizationManager authorizationManager;

    public SecurityConfig(
            UserDetailsService userDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            DynamicAuthorizationManager authorizationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authorizationManager = authorizationManager;
    }

    @Value("${demo.security.ignore-path}")
    private String[] ignorePath;

    /**
     * * 配置认证管理器
     *
     * @param http HttpSecurity
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    /**
     * * 密码编码器
     * * 使用 BCryptPasswordEncoder 进行密码加密
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * * 配置安全过滤链
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF 保护
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用默认的表单登录和基本认证
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用默认的注销功能
                .logout(AbstractHttpConfigurer::disable)
                // 配置会话管理
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 添加自定义的 JWT 认证过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置请求授权
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ignorePath).permitAll()
                        .anyRequest().access(authorizationManager)
                );

        return http.build();
    }
}
