package ium.pethub.controller;

import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.owner.response.OwnerFollowListResponseDto;
import ium.pethub.dto.owner.response.OwnerFollowResponseDto;
import ium.pethub.dto.vet.response.VetFollowListResponseDto;
import ium.pethub.service.FollowService;
import ium.pethub.util.AuthenticationPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/vet/{vetId}/follow")
    public ResponseEntity following(@AuthenticationPrincipal Long userId, @PathVariable String vetId){
        OwnerFollowResponseDto responseDto = followService.saveFollow(userId, Long.parseLong(vetId));

        return ResponseEntity.ok().body(new ResponseDto("success", responseDto));
    }

    @DeleteMapping("/api/vet/{vetId}/unfollow")
    public ResponseEntity unfollowing(@AuthenticationPrincipal Long userId, @PathVariable String vetId){
        OwnerFollowResponseDto responseDto = followService.deleteFollow(userId, Long.parseLong(vetId));

        return ResponseEntity.ok().body(new ResponseDto("success", responseDto));
    }

    @GetMapping("/api/vet/{vetId}/follower")
    public ResponseEntity getFollowingList(@AuthenticationPrincipal Long userId, @PathVariable Long vetId){
        List<VetFollowListResponseDto> responseDto = followService.getFollowerListByVetId(vetId);

        return ResponseEntity.ok().body(new ResponseDto("success", responseDto));
    }

    @GetMapping("/api/owner/{ownerId}/following")
    public ResponseEntity getFollowerList(@AuthenticationPrincipal Long userId, @PathVariable Long ownerId){
        List<OwnerFollowListResponseDto> responseDto = followService.getFollowingListByOwnerId(ownerId);

        return ResponseEntity.ok().body(new ResponseDto("success", responseDto));
    }
}
