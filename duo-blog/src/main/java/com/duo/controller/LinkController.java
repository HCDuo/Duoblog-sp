package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/27 16:47
 */
@RestController
@RequestMapping("/link")
@Api(tags = "友链",description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(BusinessName = "友链信息")
    @ApiOperation(value = "友链信息",notes = "获取友链信息")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
