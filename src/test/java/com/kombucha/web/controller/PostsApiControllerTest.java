package com.kombucha.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kombucha.domain.posts.Posts;
import com.kombucha.service.posts.PostsService;
import com.kombucha.web.dto.posts.PostsMinimalResponseDto;
import com.kombucha.web.dto.posts.PostsResponseDto;
import com.kombucha.web.dto.posts.PostsSaveRequestDto;
import com.kombucha.web.dto.posts.PostsSimpleResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostsApiController.class)
public class PostsApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean()
    private PostsService postsService;

    @Test
    public void PostsAPI_로_모든_게시글을_조회한다() throws Exception {
        // given
        String postTitle = "게시글 제목1";
        String postContent = "게시글 내용1";
        String postAuthor = "관리자";

        given(postsService.findAll())
                .willReturn(
                        Arrays.asList(PostsSimpleResponseDto.builder()
                                .entity(Posts.builder()
                                        .id(1L)
                                        .title(postTitle)
                                        .content(postContent)
                                        .author(postAuthor)
                                        .build())
                        .build()));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/posts")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(HttpStatus.OK.name()));
    }

    @Test
    public void PostsAPI_로_한_게시글을_조회한다() throws Exception {
        // given
        String postTitle = "게시글 제목1";
        String postContent = "게시글 내용1";
        String postAuthor = "관리자";

        given(postsService.findById(1L)).willReturn(PostsResponseDto.builder()
                        .entity(Posts.builder()
                                .id(1L)
                                .title(postTitle)
                                .content(postContent)
                                .author(postAuthor)
                                .build())
                .build());

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(postTitle))
                .andExpect(jsonPath("$.data.content").value(postContent))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(HttpStatus.OK.name()));
    }

    @Test
    public void PostsAPI_로_등록한다() throws Exception {
        // given
        String postTitle = "게시글 제목1";
        String postContent = "게시글 내용1";
        String postAuthor = "관리자";

        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .title(postTitle)
                .content(postContent)
                .author(postAuthor)
                .build();

        given(postsService.save(postsSaveRequestDto)).willReturn(
                PostsMinimalResponseDto.builder().postId(1L)
                        .build());

        // when
        String content = objectMapper.writeValueAsString(postsSaveRequestDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/posts")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(HttpStatus.OK.name()));
    }

    @Test
    public void PostsAPI_로_제목없이_등록하면_에러코드_반환한다() throws Exception {
        // given
        String postContent = "게시글 내용";
        String postAuthor = "관리자";

        String content = objectMapper.writeValueAsString(PostsSaveRequestDto.builder()
                .content(postContent)
                .author(postAuthor)
                .build());

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/posts")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        // todo 형식 변경 가능한지 {"type":"about:blank","title":"Bad Request","status":400,"detail":"Invalid request content.","instance":"/api/v1/posts"}
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void PostsAPI_로_내용없이_등록하면_에러코드_반환한다() throws Exception {
        // given
        String postTitle = "게시글 제목";
        String postAuthor = "관리자";

        String content = objectMapper.writeValueAsString(PostsSaveRequestDto.builder()
                .title(postTitle)
                .author(postAuthor)
                .build());

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/posts")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void Posts_제목이_max_넘으면_에러코드_반환한다() throws Exception {

        // given
        String postTitle = "김준석님 다음 생일에는 맥 프로나 아니면 갤럭시 플립 5를 사주세요. 제가 뻔뻔하다고 생각하시겠지만, 뻔뻔한건 사실이니 딱히 부정할 것도 없네요. 엇저라구요. 전 원래 그런 사람입니다. 꼬우면, 꼬와도 가만히 계세요. 왜냐면 제가 님보다 연약하기 때문임 엇저라구요. 그럴거면 나보다 약하게 태어나던가 아니니까 님이 당하는 겁니다. 아시겟어여? 사실 제가 제일 갖고싶은건 법적 문제없이 깨끗한 1000억이지만, 준석님이 능력이 안되실테니 갤럭시와 맥 프로로 만족하는 겁니다. 아시겠습니가? 네?? 그래서 내일 마라탕 먹으러 가냐구요. 마냐구요. 내일 저도 청소를 해야하긴 하는데 청소하기 너무 귀찮네요. 청소에서 언제쯤 해방될 수 있을지 모르겠습니다. 앞으로도 이렇게 바쁘고 희미한 인생을 살아야 하는건지 언제까지 고된 길을 가야하는지 어디로 가야하는지알수없지만 알수 없지만 알수없지만 오늘도 난 이렇게 걸어가고 있네 나는 이 길에 서있고 이게 정말 나의 길인가~너무 길어서 그냥 중복으로 채울게요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요. 다음 생일 선물은 맥북 프로로 사주세요.";
        String postContent = "게시글 내용";
        String postAuthor = "관리자";

        String content = objectMapper.writeValueAsString(PostsSaveRequestDto.builder()
                .title(postTitle)
                .author(postAuthor)
                .build());

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/posts")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void PostsAPI_로_수정한다() throws Exception {
        // given
        String oldTitle = "게시글 제목1";
        String oldContent = "게시글 내용1";
        String oldAuthor = "관리자";

        String newTitle = "게시글 제목2";
        String newContent = "게시글 내용2";

        given(postsService.findById(1L)).willReturn(PostsResponseDto.builder()
                .entity(Posts.builder()
                        .id(1L)
                        .title(oldTitle)
                        .content(oldContent)
                        .author(oldAuthor)
                        .build())
                .build());

        String content = objectMapper.writeValueAsString(PostsSaveRequestDto.builder()
                .title(newTitle)
                .content(newContent)
                .build());

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/posts/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(HttpStatus.OK.name()));
    }
}
