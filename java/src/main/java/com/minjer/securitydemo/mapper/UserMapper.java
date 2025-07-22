package com.minjer.securitydemo.mapper;

import com.minjer.securitydemo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Minjer
 * @since 2025-07-21
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
