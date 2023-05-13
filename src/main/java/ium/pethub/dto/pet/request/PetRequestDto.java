package ium.pethub.dto.pet.request;

import ium.pethub.domain.entity.Pet;
import ium.pethub.domain.entity.User;
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

    public Pet toEntity(User user){
        return         Pet.builder()
                .name(petName)
                .gender(petGender)
                .age(petAge)
                .weight(petWeight)
                .breed(petBreed)
                .introduction(petIntroduction)
                .image(petImage)
                .owner(user)
                .build();

    }
}
