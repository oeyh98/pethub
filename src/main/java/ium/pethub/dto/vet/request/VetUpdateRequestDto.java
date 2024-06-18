package ium.pethub.dto.vet.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class VetUpdateRequestDto {
    private String vetImage;
    private String introduction;
    private String hosName;
    private String address;
    private String openHour;
    private String closeHour;
    private int rating;
    private String career;
}