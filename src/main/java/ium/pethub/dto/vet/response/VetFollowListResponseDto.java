package ium.pethub.dto.vet.response;

import ium.pethub.domain.entity.Owner;
import ium.pethub.dto.owner.response.OwnerResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VetFollowListResponseDto {
    private Long userId;

    private int vetFollowerCnt;
    private OwnerResponseDto ownerInfo;

    @Builder
    public VetFollowListResponseDto(Long userId, int vetFollowerCnt, OwnerResponseDto ownerInfo) {
        this.userId = userId;
        this.vetFollowerCnt = vetFollowerCnt;
        this.ownerInfo = ownerInfo;
    }
}
