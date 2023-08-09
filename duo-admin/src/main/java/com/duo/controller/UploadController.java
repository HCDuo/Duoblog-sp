package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/2 17:27
 */
@RestController
@Api(tags = "上传",description = "上传相关接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @SystemLog(BusinessName = "上传文件")
    @ApiOperation(value = "上传文件",notes = "上传文件")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {
        try {
            return uploadService.uploadImg(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传上传失败");
        }
    }
}
