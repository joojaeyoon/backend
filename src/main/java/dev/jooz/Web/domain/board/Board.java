package dev.jooz.Web.domain.board;

import dev.jooz.Web.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Account.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(length = 255,nullable = false)
    private String category;

    @Column(length = 255,nullable = false)
    private String title;

    @Column(length=255,nullable = false)
    private String content;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Builder
    public Board(Account account,String category,String title,String content){
        this.account=account;
        this.category=category;
        this.title=title;
        this.content=content;
        this.created_at=LocalDateTime.now();
        this.updated_at=LocalDateTime.now();
    }
}
