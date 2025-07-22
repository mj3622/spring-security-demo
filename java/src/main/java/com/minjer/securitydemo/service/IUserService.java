package com.minjer.securitydemo.service;

import com.minjer.securitydemo.entity.Result;
import com.minjer.securitydemo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Minjer
 * @since 2025-07-21
 */
public interface IUserService extends IService<User> {

    Result<String> login(User user);
}
