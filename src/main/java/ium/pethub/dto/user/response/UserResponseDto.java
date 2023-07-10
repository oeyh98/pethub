package ium.pethub.dto.user.response;

import ium.pethub.domain.entity.RoleType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponseDto {
    private Long userId;
    private UserTokenResponseDto authTokenResponseDto;
    private String email;
    private String name;
    private String nickname;
    private String userImage;
    private RoleType role;
}
