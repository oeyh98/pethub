package ium.pethub.service;

import io.jsonwebtoken.JwtException;
import ium.pethub.domain.entity.RoleType;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.user.request.UserJoinRequestDto;
import ium.pethub.dto.user.request.UserLoginRequestDto;
import ium.pethub.dto.user.response.UserResponseDto;
import ium.pethub.dto.user.response.UserTokenResponseDto;
import ium.pethub.exception.AlreadyExistException;
import ium.pethub.util.AESEncryption;
import ium.pethub.util.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static ium.pethub.util.AuthConstants.REFRESH_EXPIRE;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final OwnerService ownerService;
    private final VetService vetService;
    private final TokenProvider TokenProvider;
    private final AESEncryption aesEncryption;

    @Transactional
    public void duplicateEmail(String email) {
        if(userRepository.existsByEmail(email)){
            throw new AlreadyExistException("이미 존재하는 이메일입니다.");
        }
    }

    @Transactional
    public void duplicateCallNumber(String callNumber) {
        if (userRepository.existsByCallNumber(callNumber)){
            throw new AlreadyExistException("이미 존재하는 전화번호입니다.");
        }
    }

//    @Transactional
//    public void joinDuplicate(UserJoinRequestDto userJoinRequestDto) {
//        User user = userJoinRequestDto.toEntity();
//        duplicateEmail(user.getEmail());
//        duplicateCallNumber(user.getCallNumber());
//    }

    public void join(UserJoinRequestDto requestDto) throws Exception {
        RoleType role  = requestDto.getRole();
        User user = requestDto.toEntity(role);
        String encryptPwd = aesEncryption.encrypt(user.getPassword());
        user.resetPassword(encryptPwd);
        userRepository.save(user);

        if(role == RoleType.OWNER) {
            ownerService.ownerJoin(user);
        }
        else if(role == RoleType.VET){
            vetService.vetJoin(user);
        }
    }

    //TODO: https, 도메인
    //TODO: 이미지 반환
    @Transactional
    public UserResponseDto login(UserLoginRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        checkPassword(user.getId(), requestDto.getPassword());

        UserTokenResponseDto token = tokenProvider(user);

        UserResponseDto responseDto =
                UserResponseDto.builder()
                        .userId(user.getId())
                        .authTokenResponseDto(token)
                        .email(user.getEmail())
                        .name(user.getName())
                        .role(user.getRole())
                        .build();

        return responseDto;
    }

    @Transactional(readOnly = true)
    public void checkPassword(Long userId, String password) throws Exception {
        User user = userRepository.findById(userId).get();
        String encryptPw = aesEncryption.encrypt(password);

        if(!user.getPassword().equals(encryptPw)){
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
    }

    @Transactional
    UserTokenResponseDto tokenProvider(User user){
        String accessToken = TokenProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = TokenProvider.createRefreshToken(user.getId());

        user.updateRefreshToken(refreshToken);

        UserTokenResponseDto token = UserTokenResponseDto.builder().
                ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();

        return token;
    }

    @Transactional(readOnly = true)
    public UserTokenResponseDto updateAccessToken(Long userId, String refreshToken){
        User user = userRepository.findById(userId).get();
        if (checkRefreshToken(user.getRefreshToken(), refreshToken)) {
            UserTokenResponseDto token = tokenProvider(user);
            return token;
        } else {
            removeRefreshToken(user);
            throw new JwtException("토큰이 변조되었습니다.");
        }
    }
    boolean checkRefreshToken(String OriginalRefreshToken, String refreshToken){
        if (OriginalRefreshToken.equals(refreshToken)) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    void removeRefreshToken(User user){
        user.destroyRefreshToken();
    }

    @Transactional(readOnly = true)
    public void removeRefreshToken(Long userId) {
        User user = userRepository.findById(userId).get();
        removeRefreshToken(user);
    }

    @Transactional
    public void updatePassword(Long userId, String password) throws Exception {
        checkPassword(userId, password);
        User user = userRepository.findById(userId).get();
        String encryptedPassword = aesEncryption.encrypt(password);
        user.resetPassword(encryptedPassword);
    }


    @Transactional
    public ResponseCookie getAccessTokenCookie(String accessToken){
        return ResponseCookie.from(
                        "ACCESS_TOKEN", accessToken)
                .path("/")
                .httpOnly(true)
                .maxAge(REFRESH_EXPIRE)
                .sameSite("None")
                .secure(true)
                .build();
    }

    @Transactional
    public ResponseCookie getRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from(
                        "REFRESH_TOKEN", refreshToken)
                .path("/api/update-token")
                .httpOnly(true)
                .maxAge(REFRESH_EXPIRE)
                .sameSite("None")
                .secure(true)
                .build();
    }

    public void withdraw(Long userId) {
        User user = userRepository.findById(userId).get();
        user.withdraw();
    }

    //token??
    public UserResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId).get();
        UserResponseDto responseDto = UserResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .userImage(user.getUserImage())
                .role(user.getRole())
                .build();
        return responseDto;
    }
}
