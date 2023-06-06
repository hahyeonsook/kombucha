package com.kombucha.web.dto.posts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsMinimalResponseDto {
    private final Long id;

    @Builder
    private PostsMinimalResponseDto(Long postId) {
        this.id = postId;
    }
}
