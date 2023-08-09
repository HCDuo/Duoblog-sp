package com.duo.service;

import com.duo.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/29 16:50
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
