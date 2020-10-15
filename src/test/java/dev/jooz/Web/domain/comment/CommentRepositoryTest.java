package dev.jooz.Web.domain.comment;

import dev.jooz.Web.domain.account.Account;
import dev.jooz.Web.domain.account.AccountRepository;
import dev.jooz.Web.domain.board.Board;
import dev.jooz.Web.domain.board.BoardRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public void setUp(){
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

    @AfterAll
    public void cleanUp(){
        accountRepository.deleteAll();
    }

    @Test
    public void load_comment(){
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

    @Test
    public void test_auditing(){
        LocalDateTime now=LocalDateTime.now();
        Comment comment=Comment.builder()
                .content("Content")
                .board(board)
                .account(account).build();
        commentRepository.save(comment);



        Optional<Comment> commentOptional=commentRepository
                .findById(comment.getId());
        comment=commentOptional.get();

        assertEquals(comment.getCreated_at().isAfter(now),true);

    }
}
