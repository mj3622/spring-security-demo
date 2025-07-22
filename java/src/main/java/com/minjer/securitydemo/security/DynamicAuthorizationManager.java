package com.minjer.securitydemo.security;

import com.minjer.securitydemo.mapper.RoleMenuMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Minjer
 * @since 2025-07-21
 */
@Component
@AllArgsConstructor
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final RoleMenuMapper roleMenuMapper;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        // 获取请求URL
        String url = context.getRequest().getRequestURI();

        // 查询所有角色与菜单的关联关系
        List<String> roleNames = roleMenuMapper.getRoleNamesByMenuPattern(url);

        // 如果没有匹配的角色，则默认返回拒绝授权
        if (roleNames.isEmpty()) {
            return new AuthorizationDecision(false);
        }

        // 查询当前用户的角色
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth) || !auth.isAuthenticated()) {
            // 如果用户未认证，拒绝授权
            return new AuthorizationDecision(false);
        }

        // 检查用户角色是否在允许的角色列表中
        boolean hasRole = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(roleNames::contains);

        // 返回授权决策
        return new AuthorizationDecision(hasRole);
    }
}