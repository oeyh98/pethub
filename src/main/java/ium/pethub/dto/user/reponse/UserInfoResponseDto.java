package ium.pethub.dto.user.reponse;

import ium.pethub.domain.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

    private String nickname;
    private String userImage;


    public UserInfoResponseDto(User user){
        this.nickname = user.getNickname();
        this.userImage = user.getUserImage();
    }
}
