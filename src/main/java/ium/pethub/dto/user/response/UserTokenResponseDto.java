package ium.pethub.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserTokenResponseDto {
    private String ACCESS_TOKEN;
    private String REFRESH_TOKEN;
}
