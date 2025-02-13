package com.chloe.config;

import com.chloe.properties.AWSS3Properties;
import com.chloe.utils.AWSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Slf4j
public class S3Configuration {
    @Bean
    public S3Client s3Client(AWSS3Properties awss3Properties) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                awss3Properties.getAccessKey(),
                awss3Properties.getSecretKey()
        );

        return S3Client.builder()
                .region(Region.of(awss3Properties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
    @Bean
    public AWSUtil awsUtil(S3Client s3Client, AWSS3Properties awss3Properties) {
        return new AWSUtil(s3Client, awss3Properties.getBucketName(), awss3Properties.getRegion());
    }
}
