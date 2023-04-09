package com.kombucha.controller;

import com.kombucha.dto.PostRequestDto;
import com.kombucha.entity.Post;
import com.kombucha.service.PostService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    @GetMapping
    public List<Post> search(PostRequestDto postRequestDto) {
        return postService.search(postRequestDto);
    }
}
