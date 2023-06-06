package ium.pethub.dto.comment.request;

import ium.pethub.domain.entity.Comment;
import ium.pethub.domain.entity.Post;
import ium.pethub.domain.entity.Vet;
import lombok.Getter;


@Getter
public class CommentSaveRequestDto {
    private String content;

    public Comment toEntity(Post post, Vet vet){
        return Comment.builder()
                .post(post)
                .vet(vet)
                .content(content)
                .build();
    }
}
