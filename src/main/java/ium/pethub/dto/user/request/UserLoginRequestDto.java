package ium.pethub.dto.user.request;

import ium.pethub.domain.entity.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

    private String email;

    private String password;

    private RoleType role;

}
