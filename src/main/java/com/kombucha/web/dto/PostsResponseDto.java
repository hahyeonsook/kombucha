package com.kombucha.web.dto;

import com.kombucha.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private final Long id;
    private final String title;
    private final String content;

    @Builder
    private PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
}
