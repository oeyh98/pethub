package ium.pethub.dto.post.response;
import ium.pethub.domain.entity.Post;
import ium.pethub.domain.entity.User;
import ium.pethub.dto.user.reponse.UserInfoResponseDto;
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
    private int likeCnt;

    /**
     * 수정자: 조훈창
     * 수정일: 2023-06-01
     * 수정내용 : 게시물 User 정보를 UserInfoResponseDto 타입으로 변경
     * 
     */

    private UserInfoResponseDto user;
    // private Long userId;
    // private String nickname;
    // private String userImage;
    // private String email;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.thumbnail = post.getThumbnail();
        this.createdAt = post.getCreatedAt();

        this.user = new UserInfoResponseDto(post.getUser());
        // this.email = post.getUser().getEmail();
        // this.nickname = post.getUser().getNickname();
        // this.userImage = post.getUser().getUserImage();
    }
}

