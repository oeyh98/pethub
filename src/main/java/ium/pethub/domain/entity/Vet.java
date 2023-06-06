package ium.pethub.domain.entity;

import ium.pethub.dto.vet.request.VetUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Vet extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "vet_id")
    private Long id;

    private String vetImage;
    private String introduction;
    private String address;
    private String clinicHour;
    private int rating;
    private String career;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id")
    private User user;

    @Builder
    public Vet(User user) {
        this.user = user;
    }

    public void update(VetUpdateRequestDto requestDto){
        this.vetImage = requestDto.getVetImage();
        this.introduction = requestDto.getIntroduction();
        this.address = requestDto.getAddress();
        this.clinicHour = requestDto.getClinicHour();
        this.rating = requestDto.getRating();
        this.career = requestDto.getCareer();
    }
}
