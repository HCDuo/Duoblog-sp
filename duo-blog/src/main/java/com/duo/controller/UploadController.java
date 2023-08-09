package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/29 16:49
 */
@RestController
@Api(tags = "个人信息",description = "个人信息相关接口")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @SystemLog(BusinessName = "更新个人信息")
    @ApiOperation(value = "更新个人信息",notes = "更新个人信息")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
