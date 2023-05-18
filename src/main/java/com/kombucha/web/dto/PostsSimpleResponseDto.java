package com.kombucha.web.dto;

import com.kombucha.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSimpleResponseDto {
    private Long id;
    private String title;

    @Builder
    private PostsSimpleResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }
}
