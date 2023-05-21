package com.kombucha.service.posts;

import com.kombucha.common.exceptions.CommonException;
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

import static com.kombucha.common.StatusCode.PAGE_NOT_FOUND;

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
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
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
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new CommonException(PAGE_NOT_FOUND));
        return PostsResponseDto.builder().entity(entity).build();
    }

    public void deleteById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        postsRepository.delete(entity);
    }
}
