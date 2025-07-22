package com.minjer.securitydemo.mapper;

import com.minjer.securitydemo.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Minjer
 * @since 2025-07-21
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    List<String> getRoleNamesByMenuPattern(String pattern);
}
