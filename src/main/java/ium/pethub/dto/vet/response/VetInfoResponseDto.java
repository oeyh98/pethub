package ium.pethub.dto.vet.response;

import ium.pethub.domain.entity.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class VetInfoResponseDto {
    private Long userId;
    private String name;
    private String nickname;
    private String vetImage;

    private Long vetId;
    private String introduction;
    private String hosName;
    private String address;
    private String openHour;
    private String closeHour;
    private int rating;
    private String career;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<Comment> commentList;
    private List<Follow> followers;
    private List<Review> reviewList;
    public VetInfoResponseDto(Vet vet){
        User user = vet.getUser();
        this.userId = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.vetImage = user.getUserImage();

        this.vetId = vet.getId();
        this.introduction = vet.getIntroduction();
        this.hosName = vet.getHosName();
        this.address = vet.getAddress();
        this.openHour = vet.getOpenHour();
        this.closeHour = vet.getCloseHour();
        this.rating = vet.getRating();
        this.career = vet.getCareer();
        this.createdAt = vet.getCreatedAt();
        this.modifiedAt = vet.getModifiedAt();

        this.commentList = vet.getCommentList();
        this.followers = vet.getFollowers();
        this.reviewList = vet.getReviewList();
    }
}
