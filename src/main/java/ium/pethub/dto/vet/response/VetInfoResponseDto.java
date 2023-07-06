package ium.pethub.dto.vet.response;

import ium.pethub.domain.entity.User;
import ium.pethub.domain.entity.Vet;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VetInfoResponseDto {
    private Long userId;
    private String name;
    private String nickname;
    private String vetImage;

    private Long vetId;
    private String introduction;
    private String address;
    private String clinicHour;
    private int rating;
    private String career;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public VetInfoResponseDto(Vet vet){
        User user = vet.getUser();
        this.userId = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.vetImage = user.getUserImage();

        this.vetId = vet.getId();
        this.introduction = vet.getIntroduction();
        this.address = vet.getAddress();
        this.clinicHour = vet.getClinicHour();
        this.rating = vet.getRating();
        this.career = vet.getCareer();
        this.createdAt = vet.getCreatedAt();
        this.modifiedAt = vet.getModifiedAt();
    }
}
