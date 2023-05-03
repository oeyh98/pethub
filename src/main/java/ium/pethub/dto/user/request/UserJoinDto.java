package ium.pethub.dto.user.request;

import ium.pethub.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinDto {

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
