package dev.jooz.Web.domain.comment;

import dev.jooz.Web.domain.AuditorEntity;
import dev.jooz.Web.domain.account.Account;
import dev.jooz.Web.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends AuditorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Account.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(targetEntity = Board.class,fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @Column(length = 255,nullable = false)
    private String content;

    @Builder
    public Comment(Account account,Board board,String content){
        this.account=account;
        this.board=board;
        this.content=content;
    }
}
