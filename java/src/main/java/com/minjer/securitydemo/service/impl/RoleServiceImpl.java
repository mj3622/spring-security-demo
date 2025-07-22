package com.minjer.securitydemo.service.impl;

import com.minjer.securitydemo.entity.Role;
import com.minjer.securitydemo.mapper.RoleMapper;
import com.minjer.securitydemo.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
