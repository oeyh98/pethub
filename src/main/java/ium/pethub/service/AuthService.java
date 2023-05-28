package ium.pethub.service;

import io.jsonwebtoken.JwtException;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.user.reponse.TokenResponseDto;
import ium.pethub.dto.user.reponse.UserLoginResponseDto;
import ium.pethub.dto.user.request.UserLoginRequestDto;
import ium.pethub.dto.user.request.UserJoinRequestDto;
import ium.pethub.dto.user.request.UserPasswordRequestDto;
import ium.pethub.exception.AlreadyExistException;
import ium.pethub.util.AESEncryption;
import ium.pethub.util.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;

import static ium.pethub.util.AuthConstants.REFRESH_EXPIRE;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AESEncryption aesEncryption;

    @Transactional
    public void join(UserJoinRequestDto userJoinRequestDto) throws Exception {
        User user = userJoinRequestDto.toEntity();
        String encryptPwd = aesEncryption.encrypt(user.getPassword());
        user.resetPassword(encryptPwd);
        userRepository.save(user);
    }

    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        checkPassword(user.getId(), requestDto.getPassword());

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        user.updateRefreshToken(refreshToken);

        TokenResponseDto token = TokenResponseDto.builder().
                ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken)
                .build();
        return UserLoginResponseDto.builder()
                .tokenResponseDto(token)
                .email(user.getEmail())
                .nickname(user.getNickname())
                .userImage(user.getUserImage()).build();
    }

    @Transactional
    public void checkPassword(Long userId, String password) throws Exception {
        User user = userRepository.findById(userId).get();
        String encryptPw = aesEncryption.encrypt(password);

        if(!user.getPassword().equals(encryptPw)){
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
    }

    @Transactional
    public void updatePassword(UserPasswordRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("유저가 존재하지 않습니다."));
        String encryptedPassword = aesEncryption.encrypt(requestDto.getPassword());
        user.resetPassword(encryptedPassword);
    }


    @Transactional
    public TokenResponseDto updateAccessToken(Long userId, String refresh_token){
        String updateAccessToken;
        User user = userRepository.findById(userId).get();
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

    @Transactional
    public ResponseCookie getAccessTokenCookie(String accessToken){
        return ResponseCookie.from(
                        "ACCESS_TOKEN", accessToken)
                .path("/")
                .httpOnly(true)
                .maxAge(REFRESH_EXPIRE)
                .sameSite("Lax")
                .build();
    }

    @Transactional
    public ResponseCookie getRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from(
                        "REFRESH_TOKEN", refreshToken)
                .path("/api/update-token")
                .httpOnly(true)
                .maxAge(REFRESH_EXPIRE)
                .sameSite("Lax")
                .build();
    }
    @Transactional
    public void joinDuplicate(UserJoinRequestDto userJoinRequestDto) {
        User user = userJoinRequestDto.toEntity();
        duplicateEmail(user.getEmail());
        duplicateNickname(user.getNickname());
        duplicateEmail(user.getPhoneNumber());
    }

    @Transactional
    public void duplicateEmail(String email) {
        if(userRepository.existsByEmail(email)){
            throw new AlreadyExistException("이미 존재하는 이메일입니다.");
        }
    }

    @Transactional
    public void duplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)){
            throw new AlreadyExistException("이미 존재하는 닉네임입니다.");
        }
    }

    @Transactional
    public void duplicatePhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)){
            throw new AlreadyExistException("이미 존재하는 전화번호입니다.");
        }
    }

    @Transactional
    public void removeRefreshToken(Long userId) {
        User user = userRepository.findById(userId).get();
        user.destroyRefreshToken();
    }
}
