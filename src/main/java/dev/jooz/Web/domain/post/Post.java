package dev.jooz.Web.domain.post;

import dev.jooz.Web.domain.AuditorEntity;
import dev.jooz.Web.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "post")
public class Post extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private Long price;

    @Builder
    public Post(Account account, String category, String title, String content,Long price){
        this.account=account;
        this.category=category;
        this.title=title;
        this.content=content;
        this.price=price;
    }
}
