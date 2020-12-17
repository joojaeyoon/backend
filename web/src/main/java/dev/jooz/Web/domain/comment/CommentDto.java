package dev.jooz.Web.domain.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateReq {

        @NotEmpty
        private String content;

        @Builder
        public CreateReq(String content) {
            this.content = content;
        }

        public Comment toEntity() {
            Comment comment = Comment.builder()
                    .content(content)
                    .build();

            return comment;
        }
    }

    @Getter
    public static class CommentRes {
        private Long id;
        private String content;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;

        public CommentRes(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.created_at = comment.getCreatedAt();
            this.updated_at = comment.getUpdatedAt();
        }
    }
}
