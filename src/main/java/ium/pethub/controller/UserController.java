package ium.pethub.controller;

import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.user.reponse.UserInfoResponseDto;
import ium.pethub.dto.user.request.UserUpdateRequestDto;
import ium.pethub.service.UserService;
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
public class UserController {

    private final UserService userService;

//    TODO: 펫정보, 게시물 정보 한번에 가져오도록 변경해야함
    //유저 조회
    //id 조회는 본인만 이용될 것이기에 토큰으로
    @ValidToken
    @GetMapping("/api/user")
    public ResponseEntity<?> getUserInfoById() {
        Long userId = UserContext.userData.get().getUserId();
        UserInfoResponseDto responseDto = userService.getUserById(userId);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, responseDto));

    }

//    TODO: 펫정보, 게시물 정보 한번에 가져오도록 변경해야함
    //TODO: 에러 내용 확인
    //유저 조회
    //본인 이외의 유저 조회시 닉네임 이용
    @ValidToken
    @GetMapping("/api/user/nickname")
    public ResponseEntity<?> getUserInfoByNickname(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, userService.getUserByNickname(nickname)));
    }

    //유저 정보 수정
    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/api/user")
    public ResponseEntity<Object> updateUserInfo(@RequestBody UserUpdateRequestDto requestDto) {
        Long userId = UserContext.userData.get().getUserId();
        userService.updateUser(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/user/image")
    public ResponseEntity<?> uploadUserImage(@RequestParam("photo") MultipartFile imageFile) throws IOException {
       return ResponseEntity.ok().body(userService.uploadUserImage(imageFile,UserContext.userData.get().getUserId()));
    }
}
