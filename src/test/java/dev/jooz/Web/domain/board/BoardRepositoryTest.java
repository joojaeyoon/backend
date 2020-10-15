package dev.jooz.Web.domain.board;

import dev.jooz.Web.domain.account.Account;
import dev.jooz.Web.domain.account.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class BoardRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void load_board(){
        Account account=Account.builder()
                .email("test@gmail.com")
                .password("supersecret")
                .username("testuser")
                .build();
        accountRepository.save(account);
        boardRepository.save(
                Board.builder()
                .account(account)
                .category("cate")
                .title("titleTest")
                .content("testContent")
                .build());

        List<Board> boardList=boardRepository.findAll();

        Board board=boardList.get(0);

        assertEquals("cate",board.getCategory());
        assertEquals(account.getUsername(),board.getAccount().getUsername());

    }
}
