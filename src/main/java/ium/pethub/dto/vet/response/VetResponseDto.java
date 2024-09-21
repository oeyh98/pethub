package ium.pethub.dto.vet.response;

import ium.pethub.domain.entity.User;
import ium.pethub.domain.entity.Vet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class VetResponseDto {
    private Long userId;
    private String name;
    private String nickname;
    private String vetImage;

    private Long vetId;
    private String hosName;
    private String openHour;
    private String closeHour;
    private int rating;
    private LocalDateTime createdAt;

    public VetResponseDto(Vet vet){
        User user = vet.getUser();
        this.userId = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.vetImage = user.getUserImage();

        this.vetId = vet.getId();
        this.hosName = vet.getHosName();
        this.openHour = vet.getOpenHour();
        this.closeHour = vet.getCloseHour();
        this.rating = vet.getRating();
        this.createdAt = vet.getCreatedAt();
    }
}
