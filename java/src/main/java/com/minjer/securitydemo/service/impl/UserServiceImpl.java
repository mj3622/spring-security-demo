package com.minjer.securitydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minjer.securitydemo.entity.Result;
import com.minjer.securitydemo.entity.User;
import com.minjer.securitydemo.mapper.UserMapper;
import com.minjer.securitydemo.service.IUserService;
import com.minjer.securitydemo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Minjer
 * @since 2025-07-21
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final AuthenticationManager authenticationManager;

    @Override
    public Result<String> login(User user) {
        // 执行认证
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        // 如果认证成功，设置用户信息到SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 查询用户权限
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // 生成 JWT 令牌
        String jwt = JwtUtil.generateToken(user.getUsername(), authorities);

        return Result.success(jwt);
    }
}
