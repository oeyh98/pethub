package ium.pethub.dto.post.request;

import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String thumbnail;

    public Post toEntity(Owner owner) {
        return Post.builder()
                .owner(owner)
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .build();
    }
}
