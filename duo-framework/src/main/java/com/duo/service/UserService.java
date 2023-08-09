package com.duo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.AddUserDto;
import com.duo.domain.dto.UserDto;
import com.duo.domain.entity.User;
import com.duo.domain.vo.UserUpdateVo;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-07-28 23:24:04
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult adminArticleList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult<?> addUser(AddUserDto addUserDto);

    ResponseResult<Object> deleteUser(Long id);

    ResponseResult<UserUpdateVo> getUserInfo(Long id);

    ResponseResult<?> updateUser(UserDto userDto);
}
