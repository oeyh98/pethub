package ium.pethub.dto.pet.request;

import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.Pet;
import lombok.Getter;

@Getter
public class PetRequestDto {
    private String petName;
    private String petGender;
    private String petAge;
    private int petWeight;
    private String petBreed;
    private String petIntroduction;
    private String petImage;

    public Pet toEntity(Owner owner){
        return Pet.builder()
                .name(petName)
                .gender(petGender)
                .age(petAge)
                .weight(petWeight)
                .breed(petBreed)
                .introduction(petIntroduction)
                .image(petImage)
                .owner(owner)
                .build();

    }
}
