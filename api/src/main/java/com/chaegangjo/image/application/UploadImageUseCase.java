package com.chaegangjo.image.application;

import com.chaegangjo.s3.DefaultAmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UploadImageUseCase {

    private final DefaultAmazonS3Service defaultAmazonS3Service;

    public String execute(MultipartFile multipartFile) {
        return defaultAmazonS3Service.uploadImage(multipartFile);
    }

    public List<String> execute(List<MultipartFile> multipartFiles) {
        return defaultAmazonS3Service.uploadImages(multipartFiles);
    }
}
