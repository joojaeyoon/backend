package dev.jooz.Web.domain.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jooz.Web.domain.post.Post;
import dev.jooz.Web.domain.post.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentRestControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    public void setUp() {
        Post post = postRepository.save(Post.builder()
                .category("category")
                .content("content")
                .title("title")
                .price(Long.valueOf(100000))
                .build());
        for (int i = 0; i < 10; i++) {
            commentRepository.save(Comment.builder()
                    .post(post)
                    .content("test comment")
                    .build());
        }
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    public void create_comment() throws Exception {
        String url="/api/comment";

        CommentDto.CreateReq dto = CommentDto.CreateReq.builder()
                .content("Create Comment")
                .build();
        String cont = objectMapper.writeValueAsString(dto);

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cont)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content", is("Create Comment")))
                .andDo(print());
    }

    @Test
    @DisplayName("유효하지 않은 댓글 생성")
    public void create_invalid_comment() throws Exception{
        String url="/api/comment";

        CommentDto.CreateReq dto = CommentDto.CreateReq.builder()
                .build();
        String cont = objectMapper.writeValueAsString(dto);

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cont)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void delete_comment() throws Exception {
        String url="/api/comment/1";

        mvc.perform(delete(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 댓글 삭제 테스트")
    public void delete_not_exist_comment() throws Exception{
        String url="/api/comment/1000";
        mvc.perform(delete(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 리스트 조회 테스트")
    public void get_comment_list() throws Exception{
        String url="/api/post/1/comment";

        mvc.perform(get(url)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(10)))
                .andDo(print());
    }
}
