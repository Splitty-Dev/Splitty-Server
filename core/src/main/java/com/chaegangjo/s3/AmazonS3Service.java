package com.chaegangjo.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chaegangjo.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.chaegangjo.exception.errorcode.S3ErrorCode.FILE_UPLOAD_FAIL;

@RequiredArgsConstructor
@Service
public class AmazonS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile multipartFile) {
        if (multipartFile == null
                || multipartFile.isEmpty()
                || multipartFile.getOriginalFilename() == null) {
            return null;
        }

        String fileName = createFileName(multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        try {
            InputStream inputStream = multipartFile.getInputStream();
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata));
        } catch (IOException e) {
            throw new S3Exception(FILE_UPLOAD_FAIL);
        }

        return fileName;
//        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    public List<String> uploadImages(List<MultipartFile> multipartFiles) {
        List<String> fileNames = new ArrayList<>();

        multipartFiles.forEach(file -> {
            String fileName = uploadImage(file);
            fileNames.add(fileName);
        });

        return fileNames;
    }

    private String createFileName(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString().concat(fileExtension);
    }
}
