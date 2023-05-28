package ium.pethub.dto.post.response;

import ium.pethub.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostListResponseDto {
    private Long postId;
    private String nickname;
    private String userImage;
    private String title;
    private String thumbnail;
    private LocalDateTime createdAt;


    public PostListResponseDto(Post post) {
        this.postId = post.getId();
        this.nickname = post.getUser().getNickname();
        this.userImage = post.getUser().getUserImage();
        this.title = post.getTitle();
        this.thumbnail = post.getThumbnail();
        this.createdAt = post.getCreatedAt();
    }
}
