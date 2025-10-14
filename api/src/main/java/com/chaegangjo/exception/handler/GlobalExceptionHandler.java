package com.chaegangjo.exception.handler;


import com.chaegangjo.dto.ApiResponse;
import com.chaegangjo.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @MessageExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleException(ChatException e) {
        log.warn("[!] Chat Exception: {}", e);

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.failure(e.getErrorCode()));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(MemberException e) {
        log.warn("[!] Member Exception: {}", e);

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.failure(e.getErrorCode()));
    }

    @ExceptionHandler(WishListException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(WishListException e) {
        log.warn("[!] WishList Exception: {}", e);

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.failure(e.getErrorCode()));
    }

    @ExceptionHandler(GoodsException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(GoodsException e) {
        log.warn("[!] Goods Exception: {}", e);

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.failure(e.getErrorCode()));
    }

    @ExceptionHandler(CustomJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(CustomJwtException e) {
        log.warn("[!] Jwt Exception: {}", e);

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.failure(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.warn("[!] Global Exception: {}", e);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.internalServerError()
                .body(ApiResponse.failure(String.valueOf(status.value()), status.getReasonPhrase()));
    }
}
