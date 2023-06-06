package ium.pethub.util;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static ium.pethub.util.AuthConstants.ROLE;
import static ium.pethub.util.AuthConstants.USERID;


@Getter
@AllArgsConstructor
public class TokenUserData {
    private Long userId;
    private String userRole;

    public TokenUserData(Claims claims) {
        this.userId = Long.parseLong(String.valueOf(claims.get(USERID)));
        this.userRole = (String) claims.get(ROLE);
    }

}
