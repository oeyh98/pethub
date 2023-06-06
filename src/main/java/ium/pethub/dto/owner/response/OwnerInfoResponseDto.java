package ium.pethub.dto.owner.response;
import ium.pethub.domain.entity.Owner;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class OwnerInfoResponseDto {

    private Long userId;
    private String nickname;
    private String address;
    private LocalDate birth;
    private String ownerImage;


    public OwnerInfoResponseDto(Owner owner){
        this.userId = owner.getUser().getId();
        this.nickname = owner.getNickname();
        this.address = owner.getAddress();
        this.birth = owner.getBirth();
        this.ownerImage = owner.getOwnerImage();
    }
}
