package dev.jooz.Web.domain.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ImageDto {

    @Getter
    @NoArgsConstructor
    public static class ImageCreateResDto{
        private String name;

        @Builder
        public ImageCreateResDto(String name){
            this.name=name;
        }
    }
}
