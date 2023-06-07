package ium.pethub.controller;

import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.owner.response.OwnerFollowListResponseDto;
import ium.pethub.dto.owner.response.OwnerFollowResponseDto;
import ium.pethub.dto.vet.response.VetFollowListResponseDto;
import ium.pethub.service.FollowService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
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

    @ValidToken
    @PostMapping("/api/vet/{vetId}/follow")
    public ResponseEntity following(@PathVariable String vetId){
        Long userId = UserContext.userData.get().getUserId();
        OwnerFollowResponseDto responseDto = followService.saveFollow(userId, Long.parseLong(vetId));

        return ResponseEntity.ok().body(new ResponseDto("success", responseDto));
    }

    @ValidToken
    @DeleteMapping("/api/vet/{vetId}/unfollow")
    public ResponseEntity unfollowing(@PathVariable String vetId){
        Long userId = UserContext.userData.get().getUserId();
        OwnerFollowResponseDto responseDto = followService.deleteFollow(userId, Long.parseLong(vetId));

        return ResponseEntity.ok().body(new ResponseDto("success", responseDto));
    }

    @ValidToken
    @GetMapping("/api/vet/{vetId}/follower")
    public ResponseEntity getFollowingList(@PathVariable Long vetId){
        List<VetFollowListResponseDto> responseDto = followService.getFollowerListByVetId(vetId);

        return ResponseEntity.ok().body(new ResponseDto("success", responseDto));
    }

    @ValidToken
    @GetMapping("/api/owner/{ownerId}/following")
    public ResponseEntity getFollowerList(@PathVariable Long ownerId){
        List<OwnerFollowListResponseDto> responseDto = followService.getFollowingListByOwnerId(ownerId);

        return ResponseEntity.ok().body(new ResponseDto("success", responseDto));
    }
}
