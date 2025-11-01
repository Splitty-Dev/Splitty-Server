package com.chaegangjo.s3;

import static com.chaegangjo.exception.errorcode.S3ErrorCode.JSON_FILE_UPLOAD_FAIL;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chaegangjo.exception.S3Exception;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecommendationAmazonS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.default-bucket.s3.bucket}")
    private String bucketName;

    public RecommendationAmazonS3Service(@Qualifier("defaultAmazonS3Client")
                                  AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void uploadJsonLogs(List<Map<String, Object>> logs, String s3Key) {
        try {
            String json = objectMapper.writeValueAsString(logs);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType("application/json");

            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
                amazonS3Client.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
            }
        } catch (Exception e) {
            throw new S3Exception(JSON_FILE_UPLOAD_FAIL);
        }
    }
}
