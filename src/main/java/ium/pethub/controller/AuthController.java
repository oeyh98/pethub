package ium.pethub.controller;

import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.user.reponse.TokenResponseDto;
import ium.pethub.dto.user.reponse.UserLoginResponseDto;
import ium.pethub.dto.user.request.UserLoginRequestDto;
import ium.pethub.dto.user.request.UserJoinRequestDto;
import ium.pethub.dto.user.request.UserPasswordRequestDto;
import ium.pethub.service.AuthService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/api/auth/duplicate-email")
    public ResponseEntity<Object> duplicateEmail(@RequestBody Map<String, String> email) {
        String mail = email.get("email");
        authService.duplicateEmail(mail);
        return ResponseEntity.ok().body(ResponseDto.of("사용 가능한 이메일입니다"));
    }

    @PostMapping("/api/auth/duplicate-nickname")
    public ResponseEntity<Object> duplicateNickname(@RequestBody Map<String, String> nickname) {
        String nick = nickname.get("nickname");
        authService.duplicateNickname(nick);
        return ResponseEntity.ok().body(ResponseDto.of("사용 가능한 닉네임입니다"));
    }

    @PostMapping("/api/auth/duplicate-phone")
    public ResponseEntity<Object> duplicatePhone(@RequestBody Map<String, String> phoneNumber) {
        String phone = phoneNumber.get("phoneNumber");
        authService.duplicatePhoneNumber(phone);
        return ResponseEntity.ok().body(ResponseDto.of("사용 가능한 번호입니다"));

    }
    @PostMapping("/api/auth/join")
    public ResponseEntity<Object> join(@RequestBody UserJoinRequestDto userJoinRequestDto) throws Exception {
        authService.joinDuplicate(userJoinRequestDto);
        authService.join(userJoinRequestDto);
        return ResponseEntity.ok().body(ResponseDto.of(
                "회원가입에 성공하였습니다."
        ));
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto request) throws Exception {
        UserLoginResponseDto responseDto = authService.login(request);

        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                responseDto.getTokenResponseDto().getACCESS_TOKEN());

        ResponseCookie RefreshToken = authService.getRefreshTokenCookie(
                responseDto.getTokenResponseDto().getREFRESH_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .header("Set-Cookie", RefreshToken.toString())
                .body(ResponseDto.of("로그인을 성공하였습니다",
                        responseDto));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/auth/logout")
    public ResponseEntity<Object> logout() {
        authService.removeRefreshToken(UserContext.userData.get().getUserId());
        return ResponseEntity.ok()
                .header("Set-Cookie", "ACCESS_TOKEN=; path=/; max-age=0; expires=0;")
                .header("Set-Cookie", "REFRESH_TOKEN=; path=/updateToken; max-age=0; expires=0;")
                .body(ResponseDto.of(
                        "로그아웃 성공")
                );
    }

    @ValidToken
    @PostMapping("/api/auth/check-pw")
    public ResponseEntity<?> checkPassword(@RequestBody Map<String, String> password) throws Exception {
        authService.checkPassword(UserContext.userData.get().getUserId(), password.get("password"));
        return ResponseEntity.ok().body(ResponseDto.of(
                "비밀번호가 일치합니다."
        ));
    }

    @ValidToken
    @PutMapping("/api/user/change-pw")
    public ResponseEntity<?> changePassword(@RequestBody UserPasswordRequestDto requestDto) throws Exception {
        authService.updatePassword(requestDto);
        return ResponseEntity.ok().body(ResponseDto.of(
                "비밀번호 변경에 성공하였습니다."
        ));
    }

    @ValidToken
    @GetMapping("/api/auth/update-token")
    public ResponseEntity<Object> updateAccessToken(@CookieValue("REFRESH_TOKEN") String refreshToken) throws Exception {

        TokenResponseDto token = authService.updateAccessToken(UserContext.userData.get().getUserId(), refreshToken);
        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                token.getACCESS_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString()).build();
    }
}
