package com.minjer.securitydemo;

import com.minjer.securitydemo.entity.User;
import com.minjer.securitydemo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SecurityDemoApplicationTests {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IUserService userService;

    @Test
    void contextLoads() {
        String password = passwordEncoder.encode("password");
        System.out.println("Encoded password: " + password);

        User user = new User();
        user.setUsername("admin");
        user.setPassword(password);
        user.setEnabled(true);
        userService.save(user);
    }

}
