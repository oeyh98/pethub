package ium.pethub.controller;


import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.user.request.UserJoinRequestDto;
import ium.pethub.dto.user.request.UserLoginRequestDto;
import ium.pethub.dto.user.response.UserLoginResponseDto;
import ium.pethub.dto.user.response.UserTokenResponseDto;
import ium.pethub.service.UserService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static ium.pethub.util.AuthConstants.*;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

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


    @PostMapping("/api/user/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequestDto request) throws Exception {
        UserLoginResponseDto responseDto = userService.login(request);

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

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @PostMapping("/api/user/logout")
    public ResponseEntity<Object> logout() {
        userService.removeRefreshToken(UserContext.userData.get().getUserId());
        return ResponseEntity.ok()
                .header("Set-Cookie", "ACCESS_TOKEN=; path=/; max-age=0; expires=0;")
                .header("Set-Cookie", "REFRESH_TOKEN=; path=/updateToken; max-age=0; expires=0;")
                .body(ResponseDto.of(
                        "로그아웃 성공")
                );
    }

    @ValidToken
    @PostMapping("/api/user/check-pw")
    public ResponseEntity<?> checkPassword(@RequestBody Map<String, String> password) throws Exception {
        userService.checkPassword(UserContext.userData.get().getUserId(), password.get(PASSWORD));
        return ResponseEntity.ok().body(ResponseDto.of(
                "비밀번호가 일치합니다."
        ));
    }

    @ValidToken
    @PutMapping("/api/user/change-pw")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> password) throws Exception {
        userService.updatePassword(UserContext.userData.get().getUserId(), password.get(PASSWORD));
        return ResponseEntity.ok().body(ResponseDto.of(
                "비밀번호 변경에 성공하였습니다."
        ));
    }

    @ValidToken
    @GetMapping("/api/user/update-token")
    public ResponseEntity<Object> updateAccessToken(@CookieValue(REFRESH_TOKEN) String refreshToken) throws Exception {

        UserTokenResponseDto token = userService.updateAccessToken(UserContext.userData.get().getUserId(), refreshToken);
        ResponseCookie AccessToken = userService.getAccessTokenCookie(
                token.getACCESS_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString()).build();
    }

    @ValidToken
    @PostMapping("/api/user/withdraw")
    public ResponseEntity withdraw(){
        userService.withdraw(UserContext.userData.get().getUserId());

        return ResponseEntity.ok().body(new ResponseDto("회원이 탈퇴되었습니다."));
    }
}
