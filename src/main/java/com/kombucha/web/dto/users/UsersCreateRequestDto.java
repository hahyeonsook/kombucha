package com.kombucha.web.dto.users;

import com.kombucha.domain.users.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @Positive(message = "나이는 0이상의 값으로 입력하세요.")
    private int age;
    @Size(max = 500, message = "자기소개는 500자 이하로 입력하세요.")
    private String description;

    @Builder
    public UsersCreateRequestDto(String email, String password, String name, int age, String description) {
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
