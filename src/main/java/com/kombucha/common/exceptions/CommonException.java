package com.kombucha.common.exceptions;

import com.kombucha.common.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonException extends RuntimeException {
    private final StatusCode statusCode;
}
