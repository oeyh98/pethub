package ium.pethub.controller;


import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.pet.request.PetRequestDto;
import ium.pethub.dto.pet.response.PetInfoResponseDto;
import ium.pethub.dto.pet.response.PetListResponseDto;
import ium.pethub.service.PetService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    // 펫 등록
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @PostMapping("/api/pet")
    public ResponseEntity<Object> registerPet(@AuthenticationPrincipal Long userId, @RequestBody PetRequestDto petRequestDto) {
        long petId = petService.registerPet(userId, petRequestDto);
        return ResponseEntity.ok().body(ResponseDto.of("펫 등록에 성공하였습니다", Map.of("petId", petId)));
    }

    // 펫 수정
    //TODO:주인 검증
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @PutMapping("/api/pet/{petId}")
    public ResponseEntity<Object> updatePet(@AuthenticationPrincipal Long userId, @PathVariable Long petId, @RequestBody PetRequestDto petRequestDto) {
        petService.updatePet(petId, petRequestDto);
        return ResponseEntity.ok().body(ResponseDto.of("펫 수정에 성공하였습니다"));
    }

    // 펫 삭제
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @DeleteMapping("/api/pet/{petId}")
    public ResponseEntity<Object> deletePet(@AuthenticationPrincipal Long userId, @PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.ok().body(ResponseDto.of("펫 삭제에 성공하였습니다"));
    }

    // 펫 상세 조회
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @GetMapping("/api/pet/{petId}")
    public ResponseEntity<Object> getPet(@AuthenticationPrincipal Long userId, @PathVariable Long petId) {
        PetInfoResponseDto petInfoResponseDto = petService.findPetByPetId(petId);
        return ResponseEntity.ok().body(ResponseDto.of("펫 조회에 성공하였습니다", petInfoResponseDto));
    }

    // 펫 리스트 조회
    // userId
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @GetMapping("/api/pet")
    public ResponseEntity<Object> getPetList(@AuthenticationPrincipal Long userId) {
        List<PetListResponseDto> responseDto = petService.findPetListByUserId(userId);
        return ResponseEntity.ok().body(ResponseDto.of("펫 리스트 조회에 성공하였습니다", responseDto));
    }

    // 조훈창 - 수정
    // Owner에 nickname 없음
    // 펫 리스트 조회
    // nickname
    //TODO: 소셜로그인
    // @ValidToken
    // @AuthCheck(role = AuthCheck.Role.OWNER)
    // @GetMapping("/api/pet/nickname")
    // public ResponseEntity<Object> getPetList(@RequestBody Map<String, String> nickname) {
    //     List<PetListResponseDto> responseDto = petService.findPetListByNickname(nickname.get("nickname"));
    //     return ResponseEntity.ok().body(ResponseDto.of("펫 리스트 조회에 성공하였습니다", responseDto));
    // }

    @AuthCheck(role = AuthCheck.Role.OWNER)
    @PostMapping("/api/pet/{petId}/image")
    public ResponseEntity<?> uploadUserImage(@RequestParam("photo") MultipartFile imageFile, @PathVariable Long petId) throws IOException {

        return ResponseEntity.ok().body(petService.uploadUserImage(imageFile, petId));
    }
}
