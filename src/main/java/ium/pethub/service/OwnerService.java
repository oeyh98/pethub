package ium.pethub.service;

import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.OwnerRepository;
import ium.pethub.dto.owner.response.OwnerInfoResponseDto;
import ium.pethub.dto.owner.request.OwnerUpdateRequestDto;
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
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Transactional
    public void ownerJoin(User user) throws Exception {
        Owner owner = new Owner(user, user.getName()+user.getId());
        ownerRepository.save(owner);
    }

    @Transactional(readOnly = true)
    public OwnerInfoResponseDto getOwnerById(Long userId) {
        Owner owner = ownerRepository.findByUserId(userId)
                .orElseThrow(()-> new EntityNotFoundException("유저를 찾을 수 없습니다."));
        return new OwnerInfoResponseDto(owner);
    }

    @Transactional(readOnly = true)
    public OwnerInfoResponseDto getOwnerByNickname(String nickname){
        Owner owner = ownerRepository.findByNickname(nickname)
                .orElseThrow(()-> new EntityNotFoundException("유저를 찾을 수 없습니다."));
        return new OwnerInfoResponseDto(owner);
    }


    public void updateOwner(Long userId, OwnerUpdateRequestDto requestDto) {
        Owner owner = ownerRepository.findByUserId(userId).get();
        owner.update(requestDto);
    }

    public Map<String,String> uploadOwnerImage(MultipartFile imageFile, Long userId) throws IOException {
        byte[] imageData = imageFile.getBytes();
        UUID uuid = UUID.randomUUID();
        String uploadDir = "pethub/src/main/upload/img/";
        String savedFileName = uuid.toString() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir,savedFileName);

        Files.write(path, imageData);

        Map<String,String> imagePath = new HashMap<>();
        imagePath.put("img","/upload_img/"+savedFileName);


        Owner owner = ownerRepository.findByUserId(userId).get();
        owner.setOwnerImage(imagePath.get("img"));

        return imagePath;
    }

}
