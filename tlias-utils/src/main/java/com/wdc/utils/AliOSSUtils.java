package com.wdc.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class AliOSSUtils {

    //注入配置参数实体类对象
    @Autowired
    private AliOSSProperties aliOSSProperties;

    public String upload(MultipartFile multipartFile) throws IOException {
        OSS ossClient = null;
        try {
            EnvironmentVariableCredentialsProvider credentialsProvider =
                    CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

            ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
            clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

            ossClient = OSSClientBuilder.create()
                    .endpoint(aliOSSProperties.getEndpoint())
                    .credentialsProvider(credentialsProvider)
                    .clientConfiguration(clientBuilderConfiguration)
                    .region(aliOSSProperties.getRegion())
                    .build();

            InputStream inputStream = multipartFile.getInputStream();

            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new IOException("无效的文件名: " + originalFilename);
            }
            String fileName = UUID.randomUUID().toString()
                    + originalFilename.substring(originalFilename.lastIndexOf("."));

            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(aliOSSProperties.getBucketName(), fileName, inputStream);
            ossClient.putObject(putObjectRequest);

            return aliOSSProperties.getEndpoint().split("//")[0] + "//" + aliOSSProperties.getBucketName() + "."
                    + aliOSSProperties.getEndpoint().split("//")[1] + "/" + fileName;

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, Error Message:" + oe.getErrorMessage()
                    + ", Error Code:" + oe.getErrorCode()
                    + ", Request ID:" + oe.getRequestId()
                    + ", Host ID:" + oe.getHostId());
            throw new IOException("OSS上传失败: " + oe.getErrorMessage(), oe);
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, Error Message:" + ce.getMessage());
            throw new IOException("OSS客户端异常: " + ce.getMessage(), ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}