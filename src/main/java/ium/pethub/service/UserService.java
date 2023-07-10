package ium.pethub.service;

import static ium.pethub.util.AuthConstants.REFRESH_EXPIRE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.JwtException;
import ium.pethub.domain.entity.Owner;
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

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final OwnerService ownerService;
    private final VetService vetService;
    private final TokenProvider TokenProvider;
    private final AESEncryption aesEncryption;

    // 조훈창- 수정
    // 닉네임 중복 검사를 위한 method
    @Transactional
    public void duplicateNickname(String nickname) {
        if(userRepository.existsByNickname(nickname)){
            throw new AlreadyExistException("이미 존재하는 닉네임입니다.");
        }
    }
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

        // 조훈창 - 수정
        // resposneDto : nickname userImage 추가
        UserResponseDto responseDto =
                UserResponseDto.builder()
                        .userId(user.getId())
                        .authTokenResponseDto(token)
                        .email(user.getEmail())
                        .name(user.getName())
                        .role(user.getRole())
                        .nickname(user.getNickname())
                        .userImage(user.getUserImage())
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

    // 조훈창 - 수정
    // 메소드 파라미터 수정
    @Transactional
    public void updatePassword(Long userId, String password,String newPassword) throws Exception {
        checkPassword(userId, password);
        User user = userRepository.findById(userId).get();
        String encryptedPassword = aesEncryption.encrypt(newPassword);
        user.resetPassword(encryptedPassword);
    }
    // 조훈창 - 수정
    // 닉네임 변경 메소드 추가
    @Transactional
    public void updateNickname(Long userId, String nickname) throws Exception {
        duplicateNickname(nickname);
        User user = userRepository.findById(userId).get();
        user.updateNickname(nickname);
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

    // 조훈창 - 수정
    // Owner 서비스에서 이동
    @Transactional
     public Map<String,String> uploadUserImage(MultipartFile imageFile, Long userId) throws IOException {
        byte[] imageData = imageFile.getBytes();
        UUID uuid = UUID.randomUUID();
        String uploadDir = "pethub/src/main/upload/img/";
        String savedFileName = uuid.toString() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir,savedFileName);

        Files.write(path, imageData);

        Map<String,String> imagePath = new HashMap<>();
        imagePath.put("img","/upload_img/"+savedFileName);


        User user = userRepository.findById(userId).get();

        // 조훈창 수정
        // owner set Owner이이미지 없음
        // owner.setOwnerImage(imagePath.get("img"));
        user.updateUserImage(imagePath.get("img"));
        return imagePath;
    }
}
