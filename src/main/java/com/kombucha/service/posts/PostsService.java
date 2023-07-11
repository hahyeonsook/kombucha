package com.kombucha.service.posts;

import com.kombucha.domain.posts.Posts;
import com.kombucha.domain.posts.PostsRepository;
import com.kombucha.domain.users.Users;
import com.kombucha.domain.users.UsersRepository;
import com.kombucha.web.dto.posts.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostsService {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    public PostsMinimalResponseDto save(PostsSaveRequestDto postsSaveRequestDto) {
        Users author = usersRepository.findByEmail(postsSaveRequestDto.getEmail())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Long postId = postsRepository.save(postsSaveRequestDto.toEntity(author)).getId();
        return PostsMinimalResponseDto.builder().id(postId).build();
    }

    public PostsMinimalResponseDto update(Long id, PostsUpdateRequestDto postsUpdateRequestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        posts.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());

        Long postId = posts.getId();
        return PostsMinimalResponseDto.builder().id(postId).build();
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
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return PostsResponseDto.builder().entity(entity).build();
    }

    public void deleteById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        postsRepository.delete(entity);
    }
}
