package ium.pethub.service;

import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.user.reponse.UserInfoResponseDto;
import ium.pethub.dto.user.request.UserUpdateRequestDto;
import ium.pethub.util.AESEncryption;
import ium.pethub.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        User user = userRepository.findById(userId).get();
        user.update(requestDto);
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
