package dev.jooz.Web.domain.image;

import dev.jooz.Web.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class ImageDto {

    @Getter
    @NoArgsConstructor
    public static class ImageCreateDto {
        @NotEmpty
        private String name;

        @Builder
        public ImageCreateDto(String name){
            this.name=name;
        }

        public Image toEntity(Post post){
            return Image.builder()
                    .post(post)
                    .url(name)
                    .build();
        }
    }
}
