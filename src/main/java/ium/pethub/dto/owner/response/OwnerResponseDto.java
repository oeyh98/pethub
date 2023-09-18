package ium.pethub.dto.owner.response;

import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OwnerResponseDto {
    private Long userId;
    private String name;
    private String nickname;
    private String ownerImage;
    private String email;


    public OwnerResponseDto(Owner owner){
        User user = owner.getUser();
        this.userId = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.ownerImage = user.getUserImage();

        this.email = owner.getUser().getEmail();
    }
}

