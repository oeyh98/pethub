package ium.pethub.dto.post.response;
import ium.pethub.domain.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private Long postId;

    private Long userId;
    private String nickname;
    private String userImage;

    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int likeCnt;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.nickname = post.getUser().getNickname();
        this.userImage = post.getUser().getUserImage();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }
}

