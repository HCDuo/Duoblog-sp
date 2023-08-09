package com.duo.service;

import com.duo.domain.ResponseResult;
import com.duo.domain.entity.User;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/28 0:17
 */
public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
