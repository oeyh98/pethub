package ium.pethub.controller;


import static ium.pethub.util.AuthConstants.EMAIL;
import static ium.pethub.util.AuthConstants.NICKNAME;
import static ium.pethub.util.AuthConstants.PASSWORD;
import static ium.pethub.util.AuthConstants.REFRESH_TOKEN;
import static ium.pethub.util.AuthConstants.NEW_PASSWORD;

import java.io.IOException;
import java.util.Map;

import ium.pethub.util.AuthenticationPrincipal;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.user.request.UserJoinRequestDto;
import ium.pethub.dto.user.request.UserLoginRequestDto;
import ium.pethub.dto.user.response.UserResponseDto;
import ium.pethub.dto.user.response.UserTokenResponseDto;
import ium.pethub.service.UserService;
import ium.pethub.util.AuthCheck;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    // 조훈창- 수정
    // 닉네임 중복 검사를 위한 method
    @PostMapping("/api/user/duplicate-nickname")
    public ResponseEntity<Object> duplicateNickname(@RequestBody Map<String, String> nickname) {
        userService.duplicateNickname( nickname.get(NICKNAME));
        return ResponseEntity.ok().body(ResponseDto.of("사용 가능한 닉네임입니다"));
    }
    @PostMapping("/api/user/duplicate-email")
    public ResponseEntity<Object> duplicateEmail(@RequestBody Map<String, String> email) {
        String mail = email.get(EMAIL);
        userService.duplicateEmail(mail);
        return ResponseEntity.ok().body(ResponseDto.of("사용 가능한 이메일입니다"));
    }

    @PostMapping("/api/user/duplicate-phone")
    public ResponseEntity<Object> duplicateCallNumber(@RequestBody Map<String, String> callNumber) {
        String phone = callNumber.get("callNumber");
        userService.duplicateCallNumber(phone);
        return ResponseEntity.ok().body(ResponseDto.of("사용 가능한 번호입니다"));

    }

    @PostMapping("/api/user/join")
    public ResponseEntity<Object> join(@RequestBody UserJoinRequestDto joinRequestDto) throws Exception {
        userService.duplicateEmail(joinRequestDto.getEmail());
        userService.duplicateCallNumber(joinRequestDto.getCallNumber());
        userService.join(joinRequestDto);
        return ResponseEntity.ok().body(ResponseDto.of(
                "회원가입에 성공하였습니다."
        ));
    }

    //TODO: 반환처리
    @PostMapping("/api/user/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequestDto request) throws Exception {
        UserResponseDto responseDto = userService.login(request);

        ResponseCookie AccessToken = userService.getAccessTokenCookie(
                responseDto.getAuthTokenResponseDto().getACCESS_TOKEN());

        ResponseCookie RefreshToken = userService.getRefreshTokenCookie(
                responseDto.getAuthTokenResponseDto().getREFRESH_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .header("Set-Cookie", RefreshToken.toString())
                .body(ResponseDto.of("로그인을 성공하였습니다",
                        responseDto));
    }

    // 조훈창 - 수정
    // 쿠키 속성 추가 : sameSite, secure httpOnly
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @PostMapping("/api/user/logout")
    public ResponseEntity<Object> logout(@AuthenticationPrincipal Long userId) {
        userService.removeRefreshToken(userId);
        return ResponseEntity.ok()
                .header("Set-Cookie", "ACCESS_TOKEN=; path=/; max-age=0; expires=0; sameSite=None; secure=true; httpOnly=true;")
                .header("Set-Cookie", "REFRESH_TOKEN=; path=/updateToken; max-age=0; expires=0; sameSite=None; secure=true; httpOnly=true;")
                .body(ResponseDto.of(
                        "로그아웃 성공")
                );
    }

    @PostMapping("/api/user/check-pw")
    public ResponseEntity<?> checkPassword(@AuthenticationPrincipal Long userId, @RequestBody Map<String, String> password) throws Exception {
        userService.checkPassword(userId, password.get(PASSWORD));
        return ResponseEntity.ok().body(ResponseDto.of(
                "비밀번호가 일치합니다."
        ));
    }

    //조훈창 - 수정
    // 메소드 파라미터 수정
    @PutMapping("/api/user/change-pw")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal Long userId, @RequestBody Map<String, String> password) throws Exception {
        userService.updatePassword(userId, password.get(PASSWORD), password.get(NEW_PASSWORD));
        return ResponseEntity.ok().body(ResponseDto.of(
                "비밀번호 변경에 성공하였습니다."
        ));
    }

    //조훈창 - 수정
    // 닉네임 변경 추가
    @PutMapping("/api/user/change-nickname")
    public ResponseEntity<?> changeNickname(@AuthenticationPrincipal Long userId, @RequestBody Map<String, String> nickname) throws Exception {
        userService.updateNickname(userId, nickname.get(NICKNAME));
        return ResponseEntity.ok().body(ResponseDto.of(
                "닉네임 변경에 성공하였습니다."
        ));
    }

    @GetMapping("/api/user/update-token")
    public ResponseEntity<Object> updateAccessToken(@AuthenticationPrincipal Long userId, @CookieValue(REFRESH_TOKEN) String refreshToken) throws Exception {

        UserTokenResponseDto token = userService.updateAccessToken(userId, refreshToken);
        ResponseCookie AccessToken = userService.getAccessTokenCookie(
                token.getACCESS_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString()).build();
    }

    @PostMapping("/api/user/withdraw")
    public ResponseEntity withdraw(@AuthenticationPrincipal Long userId){
        userService.withdraw(userId);
        return ResponseEntity.ok().body(new ResponseDto("회원이 탈퇴되었습니다."));
    }

    @GetMapping("/api/user/info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Long userId){
        return ResponseEntity.ok().body(userService.getUserInfo(userId));
    }


    @GetMapping("/api/user/info/{userId}")
    public ResponseEntity<?> getUserInfoById(@PathVariable("userId") Long userId){
        return ResponseEntity.ok().body(userService.getUserInfo(userId));
    }


    // 조훈창 - 수정
    // OWNER 에 있는 이미지 업로드 이동
    @PostMapping("/api/user/image")
    public ResponseEntity<?> uploadUserImage(@AuthenticationPrincipal Long userId, @RequestParam("photo") MultipartFile imageFile) throws IOException {
       return ResponseEntity.ok().body(userService.uploadUserImage(imageFile, userId));
    }
}
