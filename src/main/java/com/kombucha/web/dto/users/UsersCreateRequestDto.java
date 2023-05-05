package com.kombucha.web.dto.users;

import com.kombucha.domain.users.Users;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersCreateRequestDto {
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일형식이 올바르지 않습니다. 다시 입력하세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    @NotBlank(message = "이름을 입력하세요.")
    @Size(min = 1, max = 100, message = "이름은 100자 이하로 입력하세요.")
    private String name;
    @NotBlank(message = "나이를 입력하세요.")
    @Positive(message = "나이는 0이상의 값으로 입력하세요.")
    private Integer age;
    @Size(max = 500, message = "자기소개는 500자 이하로 입력하세요.")
    private String description;

    @Builder
    public UsersCreateRequestDto(String email, String password, String name, Integer age, String description) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.description = description;
    }

    public Users toEntity() {
        return Users.builder()
                .email(email)
                .password(password)
                .name(name)
                .age(age)
                .description(description)
                .build();
    }
}
