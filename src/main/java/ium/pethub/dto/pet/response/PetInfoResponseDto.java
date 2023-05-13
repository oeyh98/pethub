package ium.pethub.dto.pet.response;

import ium.pethub.domain.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PetInfoResponseDto {

    private Long petId;
    private String petName;
    private String petGender;
    private String petAge;
    private int petWeight;
    private String petBreed;
    private String petIntroduction;
    private String petImage;

    public PetInfoResponseDto(Pet pet){
        this.petId = pet.getId();
        this.petName = pet.getName();
        this.petGender = pet.getGender();
        this.petAge = pet.getAge();
        this.petWeight = pet.getWeight();
        this.petBreed = pet.getBreed();
        this.petIntroduction = pet.getIntroduction();
        this.petImage = pet.getImage();
    }
}
