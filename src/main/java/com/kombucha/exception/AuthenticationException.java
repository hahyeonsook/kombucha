package com.kombucha.exception;

import com.kombucha.common.exceptions.CommonException;
import lombok.Getter;

@Getter
public class AuthenticationException extends CommonException {
    public AuthenticationException() {
        super(401, "올바르지 않은 접근입니다.");
    }
    public AuthenticationException(int errorCode, String message) {
        super(errorCode, message);
    }
}
