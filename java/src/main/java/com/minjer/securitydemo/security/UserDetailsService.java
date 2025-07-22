package com.minjer.securitydemo.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minjer.securitydemo.entity.Role;
import com.minjer.securitydemo.entity.User;
import com.minjer.securitydemo.entity.UserRole;
import com.minjer.securitydemo.mapper.UserMapper;
import com.minjer.securitydemo.service.IRoleService;
import com.minjer.securitydemo.service.IUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final IUserRoleService userRoleService;
    private final IRoleService roleService;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<>(User.class)
                        .eq(User::getUsername, username)
                        .eq(User::getEnabled, true)
        );

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // 查询用户角色id
        List<Integer> roleIds = userRoleService.list(
                new LambdaQueryWrapper<>(UserRole.class)
                        .eq(UserRole::getUserId, user.getId())
        ).stream().map(UserRole::getRoleId).toList();

        // 根据角色id查询角色信息
        List<Role> roles = roleIds.isEmpty() ? List.of() : roleService.list(
                new LambdaQueryWrapper<>(Role.class)
                        .in(Role::getId, roleIds)
        );

        // 将角色信息转换为GrantedAuthority
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        // 返回UserDetails对象
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.getEnabled())
                .authorities(authorities)
                .build();
    }
}
