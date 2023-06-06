package ium.pethub.dto.owner.resquest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class OwnerUpdateRequestDto {
    private String nickname;
    private String ownerImage;
    private LocalDate birth;
    private String address;
}