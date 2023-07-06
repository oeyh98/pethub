package ium.pethub.dto.owner.response;
import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.User;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class OwnerInfoResponseDto {

    private Long userId;
    private Long ownerId;
    private String name;
    private String nickname;
    private String address;
    private LocalDate birth;
    private String ownerImage;


    public OwnerInfoResponseDto(Owner owner){
        User user = owner.getUser();
        this.userId = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.ownerImage = user.getUserImage();

        this.ownerId = owner.getId();
        this.address = owner.getAddress();
        this.birth = owner.getBirth();
    }
}
