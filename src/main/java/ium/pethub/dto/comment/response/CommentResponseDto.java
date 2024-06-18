package ium.pethub.dto.comment.response;

import ium.pethub.domain.entity.Comment;
import ium.pethub.dto.vet.response.VetInfoResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;

    private VetInfoResponseDto vetInfoResponseDto;
    //TODO : vetInfo
    public CommentResponseDto(Comment comment){
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();

        this.vetInfoResponseDto = new VetInfoResponseDto(comment.getVet());
    }
}
