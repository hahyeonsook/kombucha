package com.kombucha.service;

import com.kombucha.dto.PostRequestDto;
import com.kombucha.entity.Post;
import com.kombucha.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    public final PostRepository postRepository;

    public List<Post> search(PostRequestDto postRequestDto) {
        if (postRequestDto.getTitle() != null && postRequestDto.getContent() != null) {
            return postRepository.findPostsByTitleOrContentContaining(postRequestDto.getTitle(), postRequestDto.getContent());
        } else if (postRequestDto.getTitle() != null) {
            return postRepository.findPostsByTitleContaining(postRequestDto.getTitle());
        } else if (postRequestDto.getContent() != null) {
            return postRepository.findPostsByContentContaining(postRequestDto.getContent());
        } else {
            return Collections.emptyList();
        }
    }
}
