package com.duo.service.impl;

import com.duo.domain.ResponseResult;
import com.duo.domain.entity.LoginUser;
import com.duo.domain.entity.User;
import com.duo.domain.vo.BlogUserLoginVo;
import com.duo.domain.vo.UserInfoVo;
import com.duo.service.BlogLoginService;
import com.duo.utils.BeanCopyUtils;
import com.duo.utils.JwtUtil;
import com.duo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <pre>
 * 该方法使用提供的用户名和密码创建一个UsernamePasswordAuthenticationToken。
 * 然后，它尝试使用authenticate方法将此令牌传递给AuthenticationManager进行用户身份验证。如果身份验证成功，则方法继续执行；
 * 否则，它会抛出一个带有消息 "用户名或密码错误" 的RuntimeException（用户名或密码不正确）。
 * 如果身份验证成功，代码会获取已认证用户的信息，包括其用户ID。
 * 在成功认证后，代码使用从认证用户获得的用户ID生成JSON Web Token（JWT）。
 * 然后，它使用"bloglogin:"与用户ID拼接作为键，将用户信息（以LoginUser对象的形式）存储在Redis缓存中。
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 0:17
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    @Transactional
    public ResponseResult login(User user) {
        /*
        是Spring Security提供的一个特殊的Authentication实现，用于在用户名和密码等简单的凭据情况下进行认证。
        在这里，通过使用user.getUserName()和user.getPassword()来获取用户提供的用户名和密码，创建了一个新的UsernamePasswordAuthenticationToken对象。
        这个步骤将用户的用户名和密码打包成一个认证凭据对象，以便后续提交给AuthenticationManager进行用户身份验证。
        */
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        /*
        在这里，通过调用authenticationManager.authenticate(authenticationToken)方法，将之前创建的UsernamePasswordAuthenticationToken提交给AuthenticationManager进行用户身份验证。
        authenticate方法返回一个Authentication对象，表示用户的认证结果。如果身份验证成功，该对象将包含有关已认证用户的信息；如果身份验证失败，将引发异常。
        在身份验证成功后，authenticate对象中将包含用户的详细信息，如用户角色、权限等。在后续代码中，可以通过这个Authentication对象来获取用户的信息。
        */
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    @Transactional
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }
}
