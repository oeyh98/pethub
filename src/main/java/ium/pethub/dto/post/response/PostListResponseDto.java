package ium.pethub.dto.post.response;

import ium.pethub.domain.entity.Post;
import ium.pethub.dto.comment.response.CommentResponseDto;
import ium.pethub.dto.owner.response.OwnerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostListResponseDto {
    private Long postId;
    private String title;
    private String thumbnail;
    private String content;
    private LocalDateTime createdAt;

    private List<CommentResponseDto> commentList;
    private OwnerResponseDto ownerInfo;

    public PostListResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.thumbnail = post.getThumbnail();
        this.createdAt = post.getCreatedAt();
        this.commentList = post.getCommentList().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        this.ownerInfo = new OwnerResponseDto(post.getOwner());
        this.content = post.getContent();
    }
}
