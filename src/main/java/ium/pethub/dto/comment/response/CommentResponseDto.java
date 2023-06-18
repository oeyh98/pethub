package ium.pethub.dto.comment.response;

import ium.pethub.domain.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;

    //TODO : vetInfo
    public CommentResponseDto(Comment comment){
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
