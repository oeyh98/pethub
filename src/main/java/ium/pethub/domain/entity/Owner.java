package ium.pethub.domain.entity;

import ium.pethub.dto.owner.resquest.OwnerUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "owner")
public class Owner extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "owner_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;

    private String address;

    private LocalDate birth;

    private String ownerImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<Pet> petList = new ArrayList<>();

    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follow> followings = new ArrayList<>();

    @Builder
    public Owner(User user, String nickname) {
        this.user = user;
        this.nickname = nickname;
    }


    public void update(OwnerUpdateRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.ownerImage = requestDto.getOwnerImage();
        this.birth = requestDto.getBirth();
        this.address = requestDto.getAddress();
    }


    public void setOwnerImage(String ownerImage) {
        this.ownerImage = ownerImage;
    }
}
