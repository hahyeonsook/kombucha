package com.kombucha.service.posts;

import com.kombucha.domain.posts.Posts;
import com.kombucha.domain.posts.PostsRepository;
import com.kombucha.web.dto.PostsResponseDto;
import com.kombucha.web.dto.PostsSaveRequestDto;
import com.kombucha.web.dto.PostsSimpleResponseDto;
import com.kombucha.web.dto.PostsUpdateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) {
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto postsUpdateRequestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("수정할 수 없는 게시글입니다."));
        posts.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());
        return id;
    }

    public List<PostsSimpleResponseDto> findAll() {
        List<Posts> entityList = postsRepository.findAll();
        return entityList.stream()
                .map((entity)->PostsSimpleResponseDto.builder()
                        .entity(entity)
                        .build())
                .collect(Collectors.toList());
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return PostsResponseDto.builder().entity(entity).build();
    }

    public void deleteById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        entity.delete();
    }
}
