package dev.jooz.Web.domain.account;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("Account 생성 테스트")
    public void create_account() throws Exception {
        AccountDto.CreateReq dto = AccountDto.CreateReq.builder()
                .email("test@gmail.com")
                .username("testUser")
                .password("password")
                .build();
        AccountDto.AccountRes res = AccountDto.AccountRes.builder()
                .account(dto.toEntity())
                .build();

        String cont = objectMapper.writeValueAsString(dto);
        String response = objectMapper.writeValueAsString(res);


        mvc.perform(post("/api/account")
                .content(cont)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("유효하지않은 데이터 형식")
    public void create_invalid_account() throws Exception {
        AccountDto.CreateReq dto = AccountDto.CreateReq.builder()
                .email("test@gmail.com")
                .username("testuser")
                .build();
        AccountDto.AccountRes res = AccountDto.AccountRes.builder()
                .account(dto.toEntity())
                .build();

        String cont = objectMapper.writeValueAsString(dto);
        String response = objectMapper.writeValueAsString(res);

        mvc.perform(post("/api/account")
                .content(cont)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{code:'INP_001'}"))
                .andDo(print());
    }

    @Test
    @DisplayName("중복 이메일로 계정 생성")
    public void create_account_with_exist_email() throws Exception {
        AccountDto.CreateReq dto=AccountDto.CreateReq.builder()
                .email("test@test.com")
                .username("testuser")
                .password("password")
                .build();
        accountService.save(dto);

        String cont=objectMapper.writeValueAsString(dto);

        mvc.perform(post("/api/account")
                .content(cont)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{code: 'AC_002'}"));
    }
}
