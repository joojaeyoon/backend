package dev.jooz.Web.domain.comment;

import dev.jooz.Web.domain.AuditorEntity;
import dev.jooz.Web.domain.account.Account;
import dev.jooz.Web.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne(targetEntity = Post.class,fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @Column(length = 255,nullable = false)
    private String content;

    @Builder
    public Comment(Account account, Post post, String content){
        this.account=account;
        this.post = post;
        this.content=content;
    }
}
