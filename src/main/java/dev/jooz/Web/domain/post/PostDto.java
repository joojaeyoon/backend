package dev.jooz.Web.domain.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.jooz.Web.domain.image.ImageDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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

        private List<ImageDto.ImageCreateDto> images;

        @Builder
        public CreateReq(String category,String title,String content,Long price,List<ImageDto.ImageCreateDto> images){
            this.category=category;
            this.title=title;
            this.content=content;
            this.price=price;
            this.images=images;
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
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq{
        private String category;

        private String title;

        private String content;

        private Long price;

        @Builder
        public UpdateReq(String category, String title, String content,Long price){
            this.category=category;
            this.title=title;
            this.content=content;
            this.price=price;
        }

    }

    @Getter
    public static class PostRes{
        private Long id;
        private String category;
        private String title;
        private Long price;
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime created_at;
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime updated_at;

        private ImageDto.ImageCreateDto image;

        public PostRes(Post post){
            this.id=post.getId();
            this.title=post.getTitle();
            this.price=post.getPrice();
            this.created_at=post.getCreatedAt();
            this.updated_at=post.getUpdatedAt();
            this.category=post.getCategory();
        }
        public PostRes(Post post, ImageDto.ImageCreateDto image){
            this(post);
            this.image=image;
        }
    }

    @Getter
    public static class PostDetailRes extends PostRes{
        private String content;
        List<ImageDto.ImageCreateDto> images;

        public PostDetailRes(Post post, List<ImageDto.ImageCreateDto> images){
            super(post);
            this.content=post.getContent();
            this.images=images;
        }
    }
}
