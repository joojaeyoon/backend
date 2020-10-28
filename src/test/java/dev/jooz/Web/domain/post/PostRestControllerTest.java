package dev.jooz.Web.domain.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostRestControllerTest {
    @Autowired
    private PostService postService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("포스트 생성 테스트")
    public void create_post() throws Exception {
        PostDto.CreateReq dto = PostDto.CreateReq.builder()
                .category("test category")
                .content("test content")
                .price(Long.valueOf(35000))
                .title("test title")
                .build();

        String cont = objectMapper.writeValueAsString(dto);

        mvc.perform(post("/api/post")
                .content(cont)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{'title':'test title'}"))
                .andDo(print());
    }

    @Test
    @DisplayName("유효하지 않은 포스트 생성 시도")
    public void create_invalid_post() throws Exception {
        PostDto.CreateReq dto = PostDto.CreateReq.builder()
                .price(Long.valueOf(30000))
                .content("cont")
                .category("category")
                .build();

        String cont = objectMapper.writeValueAsString(dto);

        mvc.perform(post("/api/post")
                .content(cont)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'code': 'INP_001'}"))
                .andDo(print());
    }


}
