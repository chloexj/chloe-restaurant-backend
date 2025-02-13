package com.chloe.controller.admin;

import com.chloe.constant.MessageConstant;
import com.chloe.result.Result;
import com.chloe.utils.AWSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "Common api")
@Slf4j
public class CommonController {
//    @Autowired
//    private AliOssUtil aliOssUtil;
@Autowired
private AWSUtil awsUtil;
    @PostMapping("/upload")
    @ApiOperation("File upload function")
    public Result<String> upload(MultipartFile file){
      log.info("File upload:{}",file);
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;

//            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            String filePath = awsUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
        log.info("Fail to upload file:{}",e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
