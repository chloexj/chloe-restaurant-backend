//package com.chloe.config;
//
//import com.chloe.properties.AliOssProperties;
//import com.chloe.utils.AliOssUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
////configuration class, for creating AliOssUtil object
//@Configuration
//@Slf4j
//public class OssConfiguration {
//    @Bean
//    @ConditionalOnMissingBean //当没有这个bean的时候再创建
//    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
//        log.info("Create OSS upload util object:{}",aliOssProperties);
//        return new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getAccessKeyId(),
//                aliOssProperties.getAccessKeySecret(), aliOssProperties.getBucketName());
//    }
//}
