package ium.pethub.controller;

import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.owner.response.OwnerInfoResponseDto;
import ium.pethub.dto.owner.resquest.OwnerUpdateRequestDto;
import ium.pethub.service.OwnerService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    //TODO: 펫정보, 게시물 정보 한번에 가져오도록 변경해야함
    //TODO: userId로 ownerId찾기
    @ValidToken
    @GetMapping("/api/owner")
    public ResponseEntity<?> getOwnerInfoById() {
        Long userId = UserContext.userData.get().getUserId();
        OwnerInfoResponseDto responseDto = ownerService.getOwnerById(userId);
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, responseDto));

    }

    //TODO: 펫정보, 게시물 정보 한번에 가져오도록 변경해야함
    //유저 조회
    //본인 이외의 유저 조회시 닉네임 이용
    @ValidToken
    @GetMapping("/api/owner/nickname")
    public ResponseEntity<?> getOwnerInfoByNickname(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, ownerService.getOwnerByNickname(nickname)));
    }

    //전체 회원 조회는 불필요하다고 판단

    //유저 정보 수정
    @ValidToken
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @PutMapping("/api/owner")
    public ResponseEntity<Object> updateOwnerInfo(@RequestBody OwnerUpdateRequestDto requestDto) {
        Long userId = UserContext.userData.get().getUserId();
        ownerService.updateOwner(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @PostMapping("/api/owner/image")
    public ResponseEntity<?> uploadOwnerImage(@RequestParam("photo") MultipartFile imageFile) throws IOException {
       return ResponseEntity.ok().body(ownerService.uploadOwnerImage(imageFile,UserContext.userData.get().getUserId()));
    }

}
