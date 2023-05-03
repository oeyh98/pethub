package ium.pethub.dto.user.reponse;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDto {
    private TokenResponseDto tokenResponseDto;
    private String nickName;
    private String userImage;
}
