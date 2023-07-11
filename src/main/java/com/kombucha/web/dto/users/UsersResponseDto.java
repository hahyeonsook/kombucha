package com.kombucha.web.dto.users;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UsersResponseDto {
    private final String email;
    private final String name;
    private final int age;

    private final String description;

    @Builder
    private UsersResponseDto(String email, String name, int age, String description) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.description = description;
    }
}
