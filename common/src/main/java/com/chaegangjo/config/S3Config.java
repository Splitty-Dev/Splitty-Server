package com.chaegangjo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    //default bucket
    @Value("${cloud.aws.default-bucket.credentials.access-key}")
    private String defaultAccessKey;
    @Value("${cloud.aws.default-bucket.credentials.secret-key}")
    private String defaultSecretKey;
    @Value("${cloud.aws.default-bucket.region.static}")
    private String defaultRegion;

    //recommendation log bucket
    @Value("${cloud.aws.recommendation-bucket.credentials.access-key}")
    private String recommendationAccessKey;
    @Value("${cloud.aws.recommendation-bucket.credentials.secret-key}")
    private String recommendationSecretKey;
    @Value("${cloud.aws.recommendation-bucket.region.static}")
    private String recommendationRegion;

    @Bean
    @Qualifier("defaultAmazonS3Client")
    public AmazonS3Client defaultAmazonS3Client() {
        BasicAWSCredentials awsCredentials= new BasicAWSCredentials(defaultAccessKey, defaultSecretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(defaultRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Bean
    @Qualifier("recommendationAmazonS3Client")
    public AmazonS3Client recommendationAmazonS3Client() {
        BasicAWSCredentials awsCredentials= new BasicAWSCredentials(recommendationAccessKey, recommendationSecretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(recommendationRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
