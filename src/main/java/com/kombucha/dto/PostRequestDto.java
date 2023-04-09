package com.kombucha.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;

    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
