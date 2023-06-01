package ium.pethub.dto.user.reponse;

import ium.pethub.domain.entity.User;
import lombok.Getter;

/**
 * 수정자: 조훈창
 * 수정일 : 2023-06-01
 * 수정 내용: email 추가
 * 
 */

@Getter
public class UserInfoResponseDto {

    private String nickname;
    private String userImage;
    private String email;


    public UserInfoResponseDto(User user){
        this.nickname = user.getNickname();
        this.userImage = user.getUserImage();
        this.email = user.getEmail();
    }
}
