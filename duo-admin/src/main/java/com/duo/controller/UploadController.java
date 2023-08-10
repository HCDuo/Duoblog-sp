package com.duo.controller;

import com.duo.domain.ResponseResult;
import com.duo.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

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
    @ApiOperation(value = "上传文件",notes = "上传文件")
    public ResponseResult uploadImg(MultipartHttpServletRequest request) {
        try {
            // 从请求中获取名为 "img" 的文件列表
            List<MultipartFile> files = request.getFiles("img");

            if (files != null && !files.isEmpty()) {
                MultipartFile multipartFile = files.get(0); // 假设只上传了一个文件
                // 调用uploadService.uploadImg方法，并传递MultipartFile类型的参数
                return uploadService.uploadImg(multipartFile);
            } else {
                throw new IllegalArgumentException("没有找到上传的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传上传失败");
        }
    }
}
