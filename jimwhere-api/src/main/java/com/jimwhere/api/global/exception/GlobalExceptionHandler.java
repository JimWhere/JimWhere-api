package com.jimwhere.api.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

        ErrorCode errorCode = e.getErrorCode();

        String message = (e.getCustomMessage() != null)
                ? e.getCustomMessage()
                : errorCode.getMessage();

        ErrorResponse response = new ErrorResponse(
                errorCode.getCode(),
                message
        );

        return ResponseEntity
                .status(errorCode.getHttpStatusCode().value())
                .body(response);
    }
}