package ium.pethub.dto.user.response;

import ium.pethub.domain.entity.RoleType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginResponseDto {
    private Long userId;
    private UserTokenResponseDto authTokenResponseDto;
    private String email;
    private String name;
    private String userImage;
    private RoleType role;
}
