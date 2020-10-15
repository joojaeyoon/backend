package dev.jooz.Web.domain.comment;

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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Account.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(targetEntity = Board.class,fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @Column(length = 255,nullable = false)
    private String content;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Builder
    public Comment(Account account,Board board,String content){
        this.account=account;
        this.board=board;
        this.content=content;

        this.created_at=LocalDateTime.now();
        this.updated_at= LocalDateTime.now();
    }
}
