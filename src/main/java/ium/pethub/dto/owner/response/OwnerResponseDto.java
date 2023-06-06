package ium.pethub.dto.owner.response;

import ium.pethub.domain.entity.Owner;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OwnerResponseDto {
    private Long userId;
    private String nickname;
    private String ownerImage;


    public OwnerResponseDto(Owner owner){
        this.userId = owner.getUser().getId();
        this.nickname = owner.getNickname();
        this.ownerImage = owner.getOwnerImage();
    }
}

