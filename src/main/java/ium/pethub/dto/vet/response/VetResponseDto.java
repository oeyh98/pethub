package ium.pethub.dto.vet.response;

import ium.pethub.domain.entity.Vet;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class VetResponseDto {
    private Long vetId;
    private String vetImage;
    private String name;
    private int rating;

    public VetResponseDto(Vet vet){
        this.vetId = vet.getId();
        this.vetImage = vet.getVetImage();
        this.name = vet.getUser().getName();
        this.rating = vet.getRating();
    }
}
