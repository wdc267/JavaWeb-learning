package com.wdc.controller;

import com.aliyuncs.exceptions.ClientException;
import com.wdc.pojo.Result;
import com.wdc.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/upload")
    public Result upload(String username, Integer age, MultipartFile image) throws IOException, ClientException {
        log.info("文件上传：{},{},{}",username,age,image);
        String url = aliOSSUtils.upload(image);
//将图片上传完成后的url返回，用于浏览器回显展示
        return Result.success(url);

    }
}
