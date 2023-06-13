package com.kombucha.web.dto.users;

import lombok.Builder;
import lombok.Getter;

import static com.kombucha.common.constants.AuthConstants.TOKEN_TYPE;

@Getter
public class UsersMinimalResponseDto {
    private final String token;

    @Builder
    private UsersMinimalResponseDto(String token) {
        this.token = TOKEN_TYPE + " " + token;
    }
}
