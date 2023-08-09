package com.duo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.AddUserDto;
import com.duo.domain.dto.UserDto;
import com.duo.domain.entity.User;
import com.duo.domain.vo.*;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import com.duo.mapper.UserMapper;
import com.duo.service.RoleService;
import com.duo.service.UserService;
import com.duo.utils.BeanCopyUtils;
import com.duo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-07-28 23:24:06
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;

    @Override
    @Transactional
    public ResponseResult userInfo() {
        //获取id
        Long userId = SecurityUtils.getUserId();
        //根据id查询用户信息
        User user = getById(userId);
        //返回数据
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    @Transactional
    public ResponseResult updateUserInfo(User user) {
        //修改
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult register(User user) {
        //对数据进行判断
        //首先不能为空
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //判断是否存在
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //对密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setCreateTime(new Date());
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult adminArticleList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        // 构建查询条件(模糊查询)
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(userName)) {
            wrapper.like(User::getUserName, userName);
        }
        if (StringUtils.hasText(phonenumber)) {
            wrapper.eq(User::getPhonenumber, phonenumber);
        }
        if (StringUtils.hasText(status)) {
            wrapper.eq(User::getStatus, status);
        }
        // 分页查询
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> userPage = userMapper.selectPage(page, wrapper);

        // 组装响应数据
        PageVo pageVo = new PageVo(userPage.getRecords(), userPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> addUser(AddUserDto addUserDto) {
        //对数据进行判断
        //首先不能为空
        if(!StringUtils.hasText(addUserDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(addUserDto.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(addUserDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(addUserDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //判断是否存在
        if(userNameExist(addUserDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(addUserDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //对密码加密
        String encodePassword = passwordEncoder.encode(addUserDto.getPassword());
        addUserDto.setPassword(encodePassword);
        User user = BeanCopyUtils.copyBean(addUserDto,User.class);
        user.setCreateTime(new Date());
        //存入数据库
        save(user);
        userMapper.insertUserRole(user.getId(),addUserDto.getRoleIds());
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<Object> deleteUser(Long id) {
        //判断id
        User user = userMapper.selectById(id);
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        //逻辑删除
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,id)
                        .set(User::getDelFlag,1);
        user.setUpdateTime(new Date());
        int success = userMapper.update(user,updateWrapper);
        if (success > 0) {
            userMapper.deleteUserRole(id);
            return ResponseResult.okResult();
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseResult<UserUpdateVo> getUserInfo(Long id) {
        UserUpdateVo userVo = new UserUpdateVo();
        //找出user字段
        User user = userMapper.selectById(id);
        if (user != null){
            UserUpdateInfoVo userUpdateInfoVo = BeanCopyUtils.copyBean(user,UserUpdateInfoVo.class);
            userVo.setUser(userUpdateInfoVo);
        }else throw new SystemException(AppHttpCodeEnum.USER_NOT_EXIST);
        //找出roles字段
        ResponseResult<List<RoleSimpleVo>> roles = roleService.getAllRole();
        userVo.setRoles(roles.getData());
        //找出roleIds字段
        List<Long> roleIds = userMapper.getRoleIdsByUserId(id);
        userVo.setRoleIds(roleIds);
        return ResponseResult.okResult(userVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> updateUser(UserDto userDto) {
        //对数据进行判断
        //首先不能为空
        if(!StringUtils.hasText(userDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(userDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //找出这个id并匹配
        User user = BeanCopyUtils.copyBean(userDto,User.class);
        user.setUpdateTime(new Date());
        updateById(user);
        //找出roleIds并删除
        userMapper.deleteUserRole(user.getId());
        //插入新的roleIds
        userMapper.insertUserRole(user.getId(),userDto.getRoleIds());
        return ResponseResult.okResult();
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper) > 0;
    }
}
