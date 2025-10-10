package com.chaegangjo.exception;


import com.chaegangjo.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponse<Void>> handleMemberException(MemberException e) {
        log.warn("Member Exception: {}", e);

        return ResponseEntity.internalServerError()
                .body(ApiResponse.failure(e.getErrorCode()));
    }

    @ExceptionHandler(GoodsException.class)
    public ResponseEntity<ApiResponse<Void>> handleGoodsException(GoodsException e) {
        log.warn("Goods Exception: {}", e);

        return ResponseEntity.internalServerError()
                .body(ApiResponse.failure(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.warn("Exception: {}", e);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.internalServerError()
                .body(ApiResponse.failure(String.valueOf(status.value()), status.getReasonPhrase()));
    }
}
