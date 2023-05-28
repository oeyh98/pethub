package ium.pethub.dto.post.request;

import ium.pethub.domain.entity.Post;
import ium.pethub.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String thumbnail;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .build();
    }
}
