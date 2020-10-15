package dev.jooz.Web.domain.comment;

import dev.jooz.Web.domain.account.Account;
import dev.jooz.Web.domain.account.AccountRepository;
import dev.jooz.Web.domain.board.Board;
import dev.jooz.Web.domain.board.BoardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BoardRepository boardRepository;

    Account account;
    Board board;

    @BeforeAll
    void setUp(){
        account=Account.builder()
                .username("user")
                .password("pass")
                .email("email@gmail.com")
                .build();
        board=Board.builder()
                .account(account)
                .category("cate")
                .content("content")
                .title("title")
                .build();
        accountRepository.save(account);
        boardRepository.save(board);
    }

    @Test
    void load_comment(){
        commentRepository.save(Comment.builder()
                .account(account)
                .board(board)
                .content("Comment Test")
                .build());

        List<Comment> commentList=commentRepository.findAll();

        Comment comment=commentList.get(0);

        assertEquals("Comment Test",comment.getContent());
        assertEquals(board.getTitle(),comment.getBoard().getTitle());
        assertEquals(account.getUsername(),comment.getAccount().getUsername());
    }
}
