package ium.pethub.dto.pet.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PetListResponseDto {
    private Long petId;
    private String petName;
    private String petImage;

    @Builder
    public PetListResponseDto(Long id, String name, String image) {
        this.petId = id;
        this.petName = name;
        this.petImage = image;
    }
}
