package ium.pethub.dto.vet.response;

import ium.pethub.domain.entity.Vet;
import lombok.Getter;

@Getter
public class VetInfoResponseDto {
    private String vetImage;
    private String introduction;
    private String address;
    private String clinicHour;
    private int rating;
    private String career;

    public VetInfoResponseDto(Vet vet){
        this.vetImage = vet.getVetImage();
        this.introduction = vet.getIntroduction();
        this.address = vet.getAddress();
        this.clinicHour = vet.getClinicHour();
        this.rating = vet.getRating();
        this.career = vet.getCareer();
    }
}
