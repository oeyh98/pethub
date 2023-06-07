package ium.pethub.dto.owner.response;

import ium.pethub.dto.vet.response.VetResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OwnerFollowListResponseDto {
    private Long userId;

    private int ownerFollowingCnt;
    private VetResponseDto vetInfo;

    @Builder
    public OwnerFollowListResponseDto(Long userId, int ownerFollowingCnt, VetResponseDto vetInfo){
        this.userId = userId;
        this.ownerFollowingCnt = ownerFollowingCnt;
        this.vetInfo = vetInfo;
    }
}
