package ium.pethub.service;

import io.jsonwebtoken.JwtException;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.user.reponse.LoginResponseDto;
import ium.pethub.dto.user.reponse.TokenResponseDto;
import ium.pethub.dto.user.request.LoginRequestDto;
import ium.pethub.util.AESEncryption;
import ium.pethub.util.JwtTokenProvider;
import ium.pethub.util.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static ium.pethub.util.AuthConstants.REFRESH_EXPIRE;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AESEncryption aesEncryption;

    public void join(User user) throws Exception {
        String encryptPwd = aesEncryption.encrypt(user.getPassword());
        user.resetPassword(encryptPwd);
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        checkPassword(user.getId(), requestDto.getPassword());

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        user.updateRefreshToken(refreshToken);
        TokenResponseDto token = TokenResponseDto.builder().
                ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken)
                .build();
        return LoginResponseDto.builder()
                .tokenResponseDto(token)
                .userImage(user.getUserImage())
                .build();
    }

    public void checkPassword(Long userId, String password) throws Exception {
        User user = userRepository.findById(userId).get();
        String encryptPw = aesEncryption.encrypt(password);
        if(!user.getPassword().equals(encryptPw)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public TokenResponseDto updateAccessToken(String refresh_token){
        String updateAccessToken;

           User user = userRepository.findById(UserContext.userData.get().getUserId()).get();
           String OriginalRefreshToken = user.getRefreshToken();
           if (OriginalRefreshToken.equals(refresh_token)) {
               updateAccessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
           } else {
               user.destroyRefreshToken();
               userRepository.save(user);
               throw new JwtException("변조된 토큰");
           }

        return TokenResponseDto.builder()
                .ACCESS_TOKEN(updateAccessToken)
                .build();
    }

    public ResponseCookie getAccessTokenCookie(String accessToken){
        return ResponseCookie.from(
                        "ACCESS_TOKEN", accessToken)
                .path("/")
                .httpOnly(true)
                .maxAge(REFRESH_EXPIRE)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie getRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from(
                        "REFRESH_TOKEN", refreshToken)
                .path("/api/update-token")
                .httpOnly(true)
                .maxAge(REFRESH_EXPIRE)
                .sameSite("Lax")
                .build();
    }

    public boolean joinDuplicate(User user) {
        return userRepository.existsByEmail(user.getEmail());
    }

    public void removeRefreshToken(Long userId) {
        User user = userRepository.findById(userId).get();
        user.destroyRefreshToken();
    }
}
