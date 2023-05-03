package ium.pethub.controller;

import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.user.reponse.LoginResponseDto;
import ium.pethub.dto.user.reponse.TokenResponseDto;
import ium.pethub.dto.user.reponse.UserLoginResponseDto;
import ium.pethub.dto.user.request.LoginRequestDto;
import ium.pethub.dto.user.request.UserJoinDto;
import ium.pethub.service.AuthService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        if (userRepository.existsByEmail(email.get("email"))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/api/auth/duplicate-nickname")
    public ResponseEntity<Object> duplicateNickname(@RequestBody Map<String, String> nickname) {
        if (userRepository.existsByNickname(nickname.get("nickname"))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/api/auth/join")
    public ResponseEntity<Object> join(@RequestBody UserJoinDto userJoinDto) throws Exception {
        User user = userJoinDto.toEntity();
        if (authService.joinDuplicate(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.join(user);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) throws Exception {
        LoginResponseDto dto = authService.login(request);

        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                dto.getTokenResponseDto().getACCESS_TOKEN());

        ResponseCookie RefreshToken = authService.getRefreshTokenCookie(
                dto.getTokenResponseDto().getREFRESH_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .header("Set-Cookie", RefreshToken.toString())
                .body(new UserLoginResponseDto(dto.getNickName(), dto.getUserImage()));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/auth/logout")
    public ResponseEntity<Object> logout() {
        Long userId = UserContext.userData.get().getUserId();
        authService.removeRefreshToken(userId);
        return ResponseEntity.ok()
                .header("Set-Cookie", "ACCESS_TOKEN=; path=/; max-age=0; expires=0;")
                .header("Set-Cookie", "REFRESH_TOKEN=; path=/updateToken; max-age=0; expires=0;")
                .body(ResponseDto.of(
                        "로그아웃 성공")
                );
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/api/auth/check-pw")
    public ResponseEntity<?> checkPassword(@RequestBody Map<String, String> password) throws Exception {
        authService.checkPassword(UserContext.userData.get().getUserId(), password.get("password"));
        return ResponseEntity.ok().body(ResponseDto.of(
                "비밀번호가 일치합니다."
        ));
    }

    @ValidToken
    @GetMapping("/api/auth/update-token")
    public ResponseEntity<Object> updateAccessToken(@CookieValue("REFRESH_TOKEN") String refreshToken) throws Exception {

        TokenResponseDto token = authService.updateAccessToken(refreshToken);
        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                token.getACCESS_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString()).build();
    }
}
