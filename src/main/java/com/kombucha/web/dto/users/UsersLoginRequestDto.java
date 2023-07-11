package com.kombucha.web.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersLoginRequestDto {
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일형식이 올바르지 않습니다. 다시 입력하세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @Builder
    public UsersLoginRequestDto(String email, String password, String name, Integer age, String description) {
        this.email = email;
        this.password = password;
    }
}
