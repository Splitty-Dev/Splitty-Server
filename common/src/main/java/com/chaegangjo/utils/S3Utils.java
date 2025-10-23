package com.chaegangjo.utils;

public class S3Utils {

    private static final String S3_BUCKET_URL_PREFIX = "https://splitty-bucket.s3.ap-northeast-2.amazonaws.com/";

    public static String getImageUrl(String imageName) {
        return S3_BUCKET_URL_PREFIX + imageName;
    }
}
