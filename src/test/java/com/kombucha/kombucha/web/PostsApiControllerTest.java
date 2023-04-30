package com.kombucha.kombucha.web;

import com.kombucha.domain.posts.Posts;
import com.kombucha.domain.posts.PostsRepository;
import com.kombucha.web.dto.PostsResponseDto;
import com.kombucha.web.dto.PostsSaveRequestDto;
import com.kombucha.web.dto.PostsSimpleResponseDto;
import com.kombucha.web.dto.PostsUpdateRequestDto;
import org.assertj.core.internal.ErrorMessages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @BeforeEach
    public void insert_dummies() {
        String title = "게시글 제목";
        String content = "게시글 내용";

        for (int i = 0; i < 10; i++) {
            postsRepository.save(Posts.builder()
                    .title(title + i)
                    .content(content + i)
                    .build());
        }
    }

    @Test
    public void Posts_모든_게시글을_조회한다() throws Exception {
        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<PostsSimpleResponseDto[]> responseEntity = testRestTemplate.getForEntity(url, PostsSimpleResponseDto[].class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void Posts_등록한다() throws Exception {
        String url = "http://localhost:" + port + "/api/v1/posts";

        // given
        String title = "게시글 제목";
        String content = "게시글 내용";
        String author = "관리자";

        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        // when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, postsSaveRequestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
        assertThat(postsList.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    public void Posts_제목없이_등록하면_에러코드_반환한다() throws Exception {
        String url = "http://localhost:" + port + "/api/v1/posts";

        // given
        String content = "게시글 내용";
        String author = "관리자";

        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .content(content)
                .author(author)
                .build();

        // when
        ResponseEntity<ErrorMessages> responseEntity = testRestTemplate.postForEntity(url, postsSaveRequestDto, ErrorMessages.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void Posts_내용없이_등록하면_에러코드_반환한다() throws Exception {
        String url = "http://localhost:" + port + "/api/v1/posts";

        // given
        String title = "게시글 제목";
        String author = "관리자";

        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .title(title)
                .author(author)
                .build();

        // when
        ResponseEntity<ErrorMessages> responseEntity = testRestTemplate.postForEntity(url, postsSaveRequestDto, ErrorMessages.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void Posts_제목이_max_넘으면_에러코드_반환한다() throws Exception {
        String url = "http://localhost:" + port + "/api/v1/posts";

        // given
        String title = "김준석님 다음 생일에는 맥 프로나 아니면 갤럭시 플립 5를 사주세요. 제가 뻔뻔하다고 생각하시겠지만, 뻔뻔한건 사실이니 딱히 부정할 것도 없네요. 엇저라구요. 전 원래 그런 사람입니다. 꼬우면, 꼬와도 가만히 계세요. 왜냐면 제가 님보다 연약하기 때문임 엇저라구요. 그럴거면 나보다 약하게 태어나던가 아니니까 님이 당하는 겁니다. 아시겟어여? 사실 제가 제일 갖고싶은건 법적 문제없이 깨끗한 1000억이지만, 준석님이 능력이 안되실테니 갤럭시와 맥 프로로 만족하는 겁니다. 아시겠습니가? 네?? 그래서 내일 마라탕 먹으러 가냐구요. 마냐구요. 내일 저도 청소를 해야하긴 하는데 청소하기 너무 귀찮네요. 청소에서 언제쯤 해방될 수 있을지 모르겠습니다. 앞으로도 이렇게 바쁘고 희미한 인생을 살아야 하는건지 언제까지 고된 길을 가야하는지 어디로 가야하는지알수없지만 알수 없지만 알수없지만 오늘도 난 이렇게 걸어가고 있네 나는 이 길에 서있고 이게 정말 나의 길인가~너무 길어서 그냥 중복으로 채울게요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요.";
        String author = "관리자";

        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .title(title)
                .author(author)
                .build();

        // when
        ResponseEntity<ErrorMessages> responseEntity = testRestTemplate.postForEntity(url, postsSaveRequestDto, ErrorMessages.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void Posts_수정한다() throws Exception {
        Long id = 3L;
        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

        // given
        String title = "게시글 수정한 제목";
        String content = "게시글 수정한 내용";

        PostsUpdateRequestDto postsUpdateRequestDto = PostsUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        // when
        testRestTemplate.put(url, postsUpdateRequestDto);
        ResponseEntity<PostsResponseDto> responseEntity = testRestTemplate.getForEntity(url, PostsResponseDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(title);
        assertThat(responseEntity.getBody().getContent()).isEqualTo(content);
    }
}
