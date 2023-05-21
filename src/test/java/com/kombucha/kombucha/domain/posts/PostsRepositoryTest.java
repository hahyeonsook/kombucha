package com.kombucha.kombucha.domain.posts;

import com.kombucha.domain.posts.Posts;
import com.kombucha.domain.posts.PostsRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void 게시글_저장하고_불러온다() {
        // given
        String title = "게시글 제목";
        String content = "게시글 내용";
        String author = "관리자";

        Posts post = Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        // when
        testEntityManager.persist(post);

        // then
        Posts saved = testEntityManager.find(Posts.class, post.getId());

        assertThat(post.getTitle()).isEqualTo(saved.getTitle());
        assertThat(post.getContent()).isEqualTo(saved.getContent());
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
        testEntityManager.persist(post1);

        Posts post2 = Posts.builder()
                .title(title + "2")
                .content(content + "2")
                .author(author)
                .build();
        postsRepository.save(post2);
        testEntityManager.persist(post2);

        //when
        postsRepository.deleteAll();
        //then
        assertThat(postsRepository.findAll()).hasSize(0);
    }
}
