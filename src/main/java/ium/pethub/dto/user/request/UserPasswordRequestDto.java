package ium.pethub.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPasswordRequestDto {
    private String email;
    private String password;
}
