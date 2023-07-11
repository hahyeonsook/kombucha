package com.kombucha.web.dto.posts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 1, max = 500, message = "제목은 500자 이하로 입력하세요.")
    private String title;
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @Builder
    private PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
