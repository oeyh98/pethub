package ium.pethub.dto.user.request;

import ium.pethub.domain.entity.RoleType;
import ium.pethub.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@Getter
public class UserJoinRequestDto {

    private String email;

    private String password;

    private String nickname;

    private RoleType role;

    private String name;

    private String callNumber;

    public User toEntity(RoleType role) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .role(role)
                .name(name)
                .callNumber(callNumber)
                .build();
    }
}

