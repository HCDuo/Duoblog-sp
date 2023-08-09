package com.duo.service;

import com.duo.domain.ResponseResult;
import com.duo.domain.entity.User;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/31 17:44
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
