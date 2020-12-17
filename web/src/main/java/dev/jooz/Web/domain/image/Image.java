package dev.jooz.Web.domain.image;

import dev.jooz.Web.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post_img")
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Post.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(length=255,nullable = false)
    private String url;

    @Builder
    public Image(Post post,String url){
        this.post=post;
        this.url=url;
    }
}
