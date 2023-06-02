package ium.pethub.dto.post.response;

import ium.pethub.domain.entity.Post;
import ium.pethub.dto.user.reponse.UserInfoResponseDto;
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
    private String content;
    private LocalDateTime createdAt;
    /**
     * 수정자: 조훈창
     * 수정일: 2023-06-01
     * 수정내용 : 게시물 User 정보를 UserInfoResponseDto 타입으로 변경
     *           content 추가
     * 
     */
    private UserInfoResponseDto user;
    // private String nickname;
    // private String email;
    // private String userImage;

    public PostListResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.thumbnail = post.getThumbnail();
        this.createdAt = post.getCreatedAt();
        this.content = post.getContent();

        this.user = new UserInfoResponseDto(post.getUser());
        // this.nickname = post.getUser().getNickname();
        // this.userImage = post.getUser().getUserImage();
        // this.email = post.getUser().getEmail();
    }
}
