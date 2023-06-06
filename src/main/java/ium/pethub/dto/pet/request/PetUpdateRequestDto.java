package ium.pethub.dto.pet.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PetUpdateRequestDto {
    private Long petId;
    private String petName;
    private String petGender;
    private String petAge;
    private int petWeight;
    private String petBreed;
    private String petIntroduction;
    private String petImage;
}
