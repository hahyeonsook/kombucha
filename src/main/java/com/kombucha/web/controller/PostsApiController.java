package com.kombucha.web.controller;

import com.kombucha.common.CommonResponse;
import com.kombucha.service.posts.PostsService;
import com.kombucha.web.dto.posts.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        PostsMinimalResponseDto post = postsService.save(postsSaveRequestDto);
        return CommonResponse.toResponseEntity(HttpStatus.OK, post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @Valid @RequestBody PostsUpdateRequestDto postsUpdateRequestDto) {
        PostsMinimalResponseDto post = postsService.update(id, postsUpdateRequestDto);
        return CommonResponse.toResponseEntity(HttpStatus.OK, post);
    }

    @GetMapping("/")
    public ResponseEntity<CommonResponse> findAll() {
        List<PostsSimpleResponseDto> posts = postsService.findAll();
        return CommonResponse.toResponseEntity(HttpStatus.OK, posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> findById(@PathVariable Long id) {
        PostsResponseDto post = postsService.findById(id);
        return CommonResponse.toResponseEntity(HttpStatus.OK, post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteById(@PathVariable Long id) {
        postsService.deleteById(id);
        return CommonResponse.toResponseEntity(HttpStatus.OK);
    }
}
