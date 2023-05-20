package com.kombucha.common;

import lombok.AllArgsConstructor;
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
    private final String code;
    private final String message;
    private Object data;

    public static ResponseEntity<CommonResponse> toResponseEntity(HttpStatus statusCode, Object... data) {
        return ResponseEntity
                .status(statusCode.value())
                .body(CommonResponse.builder()
                        .status(statusCode.value())
                        .code(statusCode.name())
                        .message(statusCode.getReasonPhrase())
                        .data(data)
                        .build());
    }
    public static ResponseEntity<CommonResponse> toResponseEntity(StatusCode statusCode, Object... data) {
        return ResponseEntity
                .status(statusCode.getStatus().value())
                .body(CommonResponse.builder()
                        .status(statusCode.getStatus().value())
                        .code(statusCode.name())
                        .message(statusCode.getMessage())
                        .data(data)
                        .build());
    }
}
