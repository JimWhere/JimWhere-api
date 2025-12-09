package com.jimwhere.api.global.exception;

import com.jimwhere.api.global.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {

        ErrorCode errorCode = e.getErrorCode();

        // CustomException이 가진 커스텀 메시지가 있으면 그걸 우선 사용
        String message = (e.getCustomMessage() != null)
                ? e.getCustomMessage()
                : errorCode.getMessage();

        // 실패코드도 ApiResponse 로 감싸서 보내기
        ApiResponse<?> body = ApiResponse.failure(
                errorCode.name(),
                message
        );

        return ResponseEntity
                .status(errorCode.getHttpStatusCode().value())
                .body(body);
    }
}
