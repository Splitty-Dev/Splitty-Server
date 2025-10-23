package com.chaegangjo.image.application;

import com.chaegangjo.s3.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UploadImageUseCase {

    private final AmazonS3Service amazonS3Service;

    public String execute(MultipartFile multipartFile) {
        return amazonS3Service.uploadImage(multipartFile);
    }

    public List<String> execute(List<MultipartFile> multipartFiles) {
        return amazonS3Service.uploadImages(multipartFiles);
    }
}
