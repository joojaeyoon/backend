package dev.jooz.Web.domain.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostRestControllerTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            postRepository.save(PostDto.CreateReq.builder()
                    .title("test")
                    .category("test")
                    .content("test")
                    .price(Long.valueOf(10000))
                    .build().toEntity());
        }
    }

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

    @Test
    @DisplayName("포스트 리스트 받아오기")
    public void get_post_list() throws Exception {
        MultiValueMap<String,String> param=new LinkedMultiValueMap<>();
        param.add("page","1");
        param.add("size","5");
        param.add("direction","DESC");

        mvc.perform(get("/api/post")
                .params(param)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content",hasSize(5)))
                .andDo(print());
    }
    // TODO 테스트때 DB 따로 쓰는거 배우기. 그리고 위 테스트 해결하기.
}
