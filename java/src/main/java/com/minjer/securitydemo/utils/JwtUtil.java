package com.minjer.securitydemo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Minjer
 * @since 2025-07-21
 */
public class JwtUtil {
    // JWT密钥
    private static final String SECRET_KEY = "secret-key";

    // 1天过期时间
    private static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(1);

    // 生成 JWT
    public static String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 解析 JWT
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // 获取用户名
    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // 获取角色
    public static List<String> getRolesFromToken(String token) {
        return getClaimsFromToken(token).get("roles", List.class);
    }

    // 验证令牌
    public static boolean validateToken(String token) {
        try {
            // 检查令牌是否存在
            Date expiration = getClaimsFromToken(token).getExpiration();
            // 检查令牌是否过期
            return !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}