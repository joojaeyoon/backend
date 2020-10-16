package dev.jooz.Web.domain.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long account_id;
    private Long board_id;
    private String content;

    @Builder
    public CommentRequestDto(Long account_id,Long board_id,String content){
        this.account_id=account_id;
        this.board_id=board_id;
        this.content=content;
    }

    public Comment toEntity(){
        return Comment.builder()
                .content(content)
                .build();
    }
}
