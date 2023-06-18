package ium.pethub.dto.owner.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class OwnerUpdateRequestDto {
    private String nickname;
    private String ownerImage;
    private LocalDate birth;
    private String address;
}