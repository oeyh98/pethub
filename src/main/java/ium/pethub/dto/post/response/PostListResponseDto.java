package ium.pethub.dto.post.response;

import ium.pethub.domain.entity.Post;
import ium.pethub.dto.owner.response.OwnerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostListResponseDto {
    private Long postId;
    private String title;
    private String thumbnail;
    private LocalDateTime createdAt;

    private OwnerResponseDto ownerInfo;

    public PostListResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.thumbnail = post.getThumbnail();
        this.createdAt = post.getCreatedAt();

        this.ownerInfo = new OwnerResponseDto(post.getOwner());
    }
}
