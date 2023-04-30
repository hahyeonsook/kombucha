package com.kombucha.web;

import com.kombucha.service.posts.PostsService;
import com.kombucha.web.dto.PostsResponseDto;
import com.kombucha.web.dto.PostsSaveRequestDto;
import com.kombucha.web.dto.PostsSimpleResponseDto;
import com.kombucha.web.dto.PostsUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@Valid @RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        return postsService.save(postsSaveRequestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @Valid @RequestBody PostsUpdateRequestDto postsUpdateRequestDto) {
        return postsService.update(id, postsUpdateRequestDto);
    }

    @GetMapping("/api/v1/posts")
    public List<PostsSimpleResponseDto> findAll() {
        return postsService.findAll();
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public void deleteById(@PathVariable Long id) {
        postsService.deleteById(id);
    }
}
