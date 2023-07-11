package com.kombucha.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommonResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final int code;
    private final String message;
    private Object data;

    public static ResponseEntity<CommonResponse> toResponseEntity(HttpStatus statusCode) {
        return ResponseEntity
                .status(statusCode.value())
                .body(CommonResponse.builder()
                        .status(statusCode.value())
                        .code(statusCode.value())
                        .message(statusCode.getReasonPhrase())
                        .build());
    }

    public static ResponseEntity<CommonResponse> toResponseEntity(HttpStatus statusCode, Object data) {
        return ResponseEntity
                .status(statusCode.value())
                .body(CommonResponse.builder()
                        .status(statusCode.value())
                        .code(statusCode.value())
                        .message(statusCode.getReasonPhrase())
                        .data(data)
                        .build());
    }
    public static ResponseEntity<CommonResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(CommonResponse.builder()
                        .status(errorCode.getStatus())
                        .code(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .build());
    }
    public static ResponseEntity<CommonResponse> toResponseEntity(ErrorCode errorCode, Object data) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(CommonResponse.builder()
                        .status(errorCode.getStatus())
                        .code(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .data(data)
                        .build());
    }
}
