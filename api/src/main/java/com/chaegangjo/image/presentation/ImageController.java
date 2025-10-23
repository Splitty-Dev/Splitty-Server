package com.chaegangjo.image.presentation;

import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.image.application.UploadImageUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "이미지", description = "이미지 업로드 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final UploadImageUseCase uploadImageUseCase;

    @Operation(summary = "단일 이미지 업로드", description = "이미지 파일을 multipart/form-data 형식으로 전송.")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<String>> uploadImage(MultipartFile multipartFile) {
        return ResponseEntity.ok(
                ApiResponse.success(uploadImageUseCase.execute(multipartFile)));
    }

    @Operation(summary = "다중 이미지 업로드", description = "이미지 파일들을 multipart/form-data 형식으로 전송.")
    @PostMapping(path = "/multiple", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<List<String>>> uploadImages(List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(
                ApiResponse.success(uploadImageUseCase.execute(multipartFiles)));
    }
}
