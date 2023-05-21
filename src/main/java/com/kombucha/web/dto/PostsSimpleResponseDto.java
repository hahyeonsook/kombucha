package com.kombucha.web.dto;

import com.kombucha.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsSimpleResponseDto {
    private final Long id;
    private final String title;

    @Builder
    private PostsSimpleResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }
}
