package ium.pethub.dto.owner.response;
import ium.pethub.domain.entity.*;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

@Getter
public class OwnerInfoResponseDto {

    private Long userId;
    private Long ownerId;
    private String name;
    private String nickname;
    private String address;
    private LocalDate birth;
    private String ownerImage;

    private List<Post> postList;
    private List<Follow> followings;
    private List<Pet> petList;
//    private List<Review> reviewList;

    public OwnerInfoResponseDto(Owner owner){
        User user = owner.getUser();
        this.userId = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.ownerImage = user.getUserImage();

        this.ownerId = owner.getId();
        this.address = owner.getAddress();
        this.birth = owner.getBirth();

        this.postList = owner.getPostList();
        this.followings = owner.getFollowings();
        this.petList = owner.getPetList();
//        this.reviewList = owner.getReviewList();
    }
}
