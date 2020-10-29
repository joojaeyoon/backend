package dev.jooz.Web.domain.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PostDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateReq{

        @NotEmpty
        private String category;

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        @NotNull
        private Long price;

        @Builder
        public CreateReq(String category,String title,String content,Long price){
            this.category=category;
            this.title=title;
            this.content=content;
            this.price=price;
        }
        public Post toEntity(){
            return Post.builder()
                    .title(title)
                    .content(content)
                    .category(category)
                    .price(price)
                    .build();
        }
    }


    @Getter
    public static class PostRes{
        private Long id;
        private String category;
        private String title;
        private LocalDateTime created_at;

        public PostRes(Post post){
            this.id=post.getId();
            this.title=post.getTitle();
            this.created_at=post.getCreatedAt();
            this.category=post.getCategory();
        }

    }
}
