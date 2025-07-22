package com.minjer.securitydemo.service.impl;

import com.minjer.securitydemo.entity.Menu;
import com.minjer.securitydemo.mapper.MenuMapper;
import com.minjer.securitydemo.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
