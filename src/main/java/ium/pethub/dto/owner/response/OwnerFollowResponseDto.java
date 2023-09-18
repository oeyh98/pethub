package ium.pethub.dto.owner.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OwnerFollowResponseDto {
    private Long ownerId;
    private Long vetId;

    private boolean isFollow;

    private int ownerFollowingCnt;

    private int vetFollowerCnt;

    @Builder
    public OwnerFollowResponseDto(Long ownerId, Long vetId, boolean isFollow, int ownerFollowingCnt, int vetFollowerCnt){
        this.ownerId = ownerId;
        this.vetId = vetId;
        this.isFollow = isFollow;
        this.ownerFollowingCnt = ownerFollowingCnt;
        this.vetFollowerCnt = vetFollowerCnt;
    }

}
