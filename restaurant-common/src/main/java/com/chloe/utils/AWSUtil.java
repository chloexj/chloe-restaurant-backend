package com.chloe.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
@Slf4j
@Data
@AllArgsConstructor
public class AWSUtil {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
    private S3Client s3Client;
    public AWSUtil(S3Client s3Client, String bucketName, String region) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.region = region;
    }

    public String upload(byte[] bytes, String objectName){



        try {
            // 构建上传请求
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(objectName)
                            .build();

            // 上传文件
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));

            System.out.println("File uploaded successfully to S3!");
        } catch (AwsServiceException e) {
            System.out.println("有异常。。。AwsServiceException");
            throw new RuntimeException(e);
        } catch (SdkClientException e) {
            System.out.println("有异常。。。SdkClientException");
            throw new RuntimeException(e);
        }
            //        } finally {
////            // 关闭客户端
////            s3Client.close();
//
//        }
//https://s3.us-east-1.amazonaws.com/com.chloe.testaws/2.png
        StringBuilder stringBuilder = new StringBuilder("https://s3.");
        stringBuilder
                .append(region)
                .append(".amazonaws.com/")
                .append(bucketName)
                .append("/")
                .append(objectName);

        log.info("文件上传到AWS:{}", stringBuilder.toString());

        return stringBuilder.toString();
    }

}
