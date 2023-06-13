package com.kombucha.web.dto.users;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UsersRequestDto {
    private final String email;

    @Builder
    private UsersRequestDto(String email) {
        this.email = email;
    }
}
