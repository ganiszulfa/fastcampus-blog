package com.fastcampus.blog.controller;

import com.fastcampus.blog.entity.Comment;
import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.comment.GetCommentsRequest;
import com.fastcampus.blog.response.comment.GetCommentResponse;
import com.fastcampus.blog.service.CommentService;
import com.fastcampus.blog.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentPublicControllerTests {

    @Autowired
    @InjectMocks
    CommentPublicController controller;

    @Mock
    CommentService commentService;

    private AutoCloseable mocks;

    @BeforeEach
    void beforeEach() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        mocks.close();
    }

    @Test
    void getComments_givenOneComment_shouldReturnOne() {

        GetCommentsRequest request = GetCommentsRequest.builder()
                .postSlug("slug1")
                .pageNo(0)
                .limit(10)
                .build();

        GetCommentResponse getCommentResponse = new GetCommentResponse();
        getCommentResponse.setName("ganis");
        getCommentResponse.setEmail("ganis@example.com");
        getCommentResponse.setBody("first comment!");
        getCommentResponse.setId(1);

        List<GetCommentResponse> actualCommentResponse =
                List.of(getCommentResponse);

        when(commentService.getComments(request)).thenReturn(actualCommentResponse);

        ResponseEntity<List<GetCommentResponse>> commentResponses = controller
                .getComments("slug1", 0, 10);

        Assertions.assertNotNull(commentResponses);
        Assertions.assertEquals(commentResponses.getBody().size(), 1);
        Assertions.assertEquals(commentResponses.getBody().getFirst().getId(), 1);
        Assertions.assertEquals(commentResponses.getBody().getFirst().getBody(), "first comment!");

    }

    @Test
    void getComments_givenPostInvalid_shouldThrowError() {

        GetCommentsRequest request = GetCommentsRequest.builder()
                .postSlug("slug1")
                .pageNo(0)
                .limit(10)
                .build();

        when(commentService.getComments(request))
                .thenThrow(new ApiException("post not found", HttpStatus.NOT_FOUND));

        Assertions.assertThrows(ApiException.class, () -> controller
                .getComments("slug1", 0, 10));

    }

}
