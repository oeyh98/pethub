package ium.pethub.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDto {
    @NotEmpty
    private String nickname;
    private String phoneNumber;
    private String userImage;
    private LocalDate birth;
    private String address;
}