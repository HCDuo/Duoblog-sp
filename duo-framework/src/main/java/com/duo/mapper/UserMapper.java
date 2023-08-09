package com.duo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duo.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-27 18:08:05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    boolean insertUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    void deleteUserRole(Long id);

    List<Long> getRoleIdsByUserId(Long id);
}
