package com.minjer.securitydemo.controller;


import com.minjer.securitydemo.entity.Result;
import com.minjer.securitydemo.entity.User;
import com.minjer.securitydemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 用户前端控制器
 *
 * @author Minjer
 * @since 2025-07-21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("/ping")
    public String ping() {
        return "Pong";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/info")
    public Result<Authentication> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Result.fail("User not authenticated");
        }

        return Result.success(authentication);
    }
}

