package com.minjer.securitydemo.service.impl;

import com.minjer.securitydemo.entity.UserRole;
import com.minjer.securitydemo.mapper.UserRoleMapper;
import com.minjer.securitydemo.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Minjer
 * @since 2025-07-21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
