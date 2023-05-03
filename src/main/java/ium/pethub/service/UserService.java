package ium.pethub.service;

import io.jsonwebtoken.JwtException;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.user.reponse.UserInfoResponseDto;
import ium.pethub.dto.user.request.UserPwdResetRequestDto;
import ium.pethub.dto.user.request.UserUpdateRequestDto;
import ium.pethub.util.AESEncryption;
import ium.pethub.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AESEncryption aesEncryption;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        return new UserInfoResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname).get();
        return new UserInfoResponseDto(user);
    }

    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User selectedUser = userRepository.findById(userId).get();

        selectedUser.update(requestDto);
    }

    public void updatePassword(UserPwdResetRequestDto requestDto) throws Exception {

        try {
            jwtTokenProvider.getClaims(requestDto.getToken());
        } catch (JwtException e) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        }

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("유저가 존재하지 않습니다."));
        String encryptedPassword = aesEncryption.encrypt(requestDto.getPassword());
        user.resetPassword(encryptedPassword);
    }

    public void userWithdraw(Long userId) {
        User findUser = userRepository.findById(userId).get();
        findUser.withdraw();
    }

    public Map<String,String> uploadUserImage(MultipartFile imageFile, Long userId) throws IOException {
        byte[] imageData = imageFile.getBytes();
        UUID uuid = UUID.randomUUID();
        String uploadDir = "pethub/src/main/upload/img/";
        String savedFileName = uuid.toString() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir,savedFileName);

        Files.write(path, imageData);

        Map<String,String> imagePath = new HashMap<>();
        imagePath.put("img","/upload_img/"+savedFileName);


        User user =userRepository.findById(userId).get();
        user.setUserImage(imagePath.get("img"));

        return imagePath;
    }

}
