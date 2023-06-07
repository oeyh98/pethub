package ium.pethub.dto.comment.response;

import ium.pethub.domain.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String commentId;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getContent();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
