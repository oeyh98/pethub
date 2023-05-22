package ium.pethub.dto.user.reponse;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginResponseDto {
    private TokenResponseDto tokenResponseDto;
    private String email;
    private String nickname;
    private String userImage;
}
