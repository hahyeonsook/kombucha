package com.kombucha.domain.posts;

import com.kombucha.common.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(WebConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void 게시글_저장하고_불러온다() {
        // given
        String title = "게시글 제목";
        String content = "게시글 내용";
        String author = "관리자";
        LocalDateTime now = LocalDateTime.now();

        Posts post = Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        // when
        Posts saved = postsRepository.save(post);

        // then
        assertThat(saved.getCreated()).isAfter(now);
        assertThat(saved.getTitle()).isEqualTo(title);
        assertThat(saved.getContent()).isEqualTo(content);
    }

    @Test
    public void 게시글_삭제한다() {
        // given
        String title = "게시글 제목";
        String content = "게시글 내용";
        String author = "관리자";

        Posts post1 = Posts.builder()
                .title(title + "1")
                .content(content + "1")
                .author(author)
                .build();
        postsRepository.save(post1);

        Posts post2 = Posts.builder()
                .title(title + "2")
                .content(content + "2")
                .author(author)
                .build();
        postsRepository.save(post2);

        //when
        postsRepository.deleteAll();
        //then
        assertThat(postsRepository.findAll()).hasSize(0);
    }
}
