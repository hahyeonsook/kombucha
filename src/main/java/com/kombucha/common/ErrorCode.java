package com.kombucha.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(401, "올바르지 않은 토큰입니다.");
    private final int status;
    private final String message;
}
