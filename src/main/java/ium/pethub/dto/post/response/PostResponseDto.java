package ium.pethub.dto.post.response;
import ium.pethub.domain.entity.Post;
import ium.pethub.dto.owner.response.OwnerResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {

    private Long postId;
    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    private OwnerResponseDto ownerInfo;


    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.thumbnail = post.getThumbnail();
        this.createdAt = post.getCreatedAt();

        this.ownerInfo = new OwnerResponseDto(post.getOwner());
    }
}

