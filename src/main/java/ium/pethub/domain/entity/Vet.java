package ium.pethub.domain.entity;

import ium.pethub.dto.vet.request.VetUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Vet extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "vet_id")
    private Long id;

    private String introduction;
    private String hosName;
    private String address;
    private String openHour;
    private String closeHour;
    private int rating;
    private String career;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "vet", fetch = FetchType.LAZY)
    private List<Follow> followers = new ArrayList<>();
    @OneToMany(mappedBy = "vet",fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "vet",fetch = FetchType.LAZY)
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public Vet(User user) {
        this.user = user;
    }

    public void update(VetUpdateRequestDto requestDto){
        this.introduction = requestDto.getIntroduction();
        this.hosName = requestDto.getHosName();
        this.address = requestDto.getAddress();
        this.openHour = requestDto.getOpenHour();
        this.closeHour = requestDto.getCloseHour();
        this.rating = requestDto.getRating();
        this.career = requestDto.getCareer();

        this.user.updateUserImage(requestDto.getVetImage());
    }

    public void updateRating(int rating){
        this.rating = rating;
    }
}