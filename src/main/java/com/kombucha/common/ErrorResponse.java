package com.kombucha.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(HttpStatus errorCode) {
        return ResponseEntity
                .status(errorCode.value())
                .body(ErrorResponse.builder()
                        .status(errorCode.value())
                        .error(errorCode.name())
                        .code(errorCode.name())
                        .message(errorCode.getReasonPhrase())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus())
                        .error(errorCode.getMessage())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build());
    }
}
