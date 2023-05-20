package com.kombucha.web;

import com.kombucha.common.CommonResponse;
import com.kombucha.service.posts.PostsService;
import com.kombucha.web.dto.PostsResponseDto;
import com.kombucha.web.dto.PostsSaveRequestDto;
import com.kombucha.web.dto.PostsSimpleResponseDto;
import com.kombucha.web.dto.PostsUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        postsService.save(postsSaveRequestDto);
        return CommonResponse.toResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @Valid @RequestBody PostsUpdateRequestDto postsUpdateRequestDto) {
        postsService.update(id, postsUpdateRequestDto);
        return CommonResponse.toResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/v1/posts")
    public ResponseEntity<CommonResponse> findAll() {
        List<PostsSimpleResponseDto> posts = postsService.findAll();
        return CommonResponse.toResponseEntity(HttpStatus.OK, posts);
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<CommonResponse> findById(@PathVariable Long id) {
        PostsResponseDto post = postsService.findById(id);
        return CommonResponse.toResponseEntity(HttpStatus.OK, post);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<CommonResponse> deleteById(@PathVariable Long id) {
        postsService.deleteById(id);
        return CommonResponse.toResponseEntity(HttpStatus.OK);
    }
}
