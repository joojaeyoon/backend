package dev.jooz.Web.domain.post;

import dev.jooz.Web.domain.account.Account;
import dev.jooz.Web.domain.account.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PostRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    public void load_board(){
        Account account=Account.builder()
                .email("test@gmail.com")
                .password("supersecret")
                .username("testuser")
                .build();
        accountRepository.save(account);
        postRepository.save(
                Post.builder()
                .account(account)
                .category("cate")
                .title("titleTest")
                .content("testContent")
                .build());

        List<Post> postList = postRepository.findAll();

        Post post = postList.get(0);

        System.out.println(post.toString());

        assertEquals("cate", post.getCategory());
        assertEquals(account.getUsername(), post.getAccount().getUsername());

    }
}
