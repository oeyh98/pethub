package ium.pethub.service;
import ium.pethub.domain.entity.Post;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.entity.Vet;
import ium.pethub.domain.repository.VetRepository;
import ium.pethub.dto.post.response.PostListResponseDto;
import ium.pethub.dto.vet.request.VetUpdateRequestDto;
import ium.pethub.dto.vet.response.VetInfoResponseDto;
import ium.pethub.dto.vet.response.VetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VetService {
    private final VetRepository vetRepository;

    @Transactional
    public long vetJoin(User user) throws Exception {
        Vet vet = new Vet(user);
        vetRepository.save(vet);

        return vet.getId();
    }

    @Transactional(readOnly = true)
    public VetInfoResponseDto getByVetId(Long userId){
        Vet vet = vetRepository.findByUserId(userId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 유저입니다."));

        return new VetInfoResponseDto(vet);
    }

    @Transactional(readOnly = true)
    public VetInfoResponseDto getVetByNickname(String nickname) {
        Vet vet = vetRepository.findByUserNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        return new VetInfoResponseDto(vet);
    }

    @Transactional(readOnly = true)
    public Page<VetResponseDto> getAllVets(int page){
        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdAt").descending());
        Page<Vet> vets = vetRepository.findAll(pageable);
        List<VetResponseDto> vetList = vets.stream()
                .map(VetResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(vetList, pageable, vets.getTotalElements());
    }

    @Transactional
    public void vetUpdate(Long userId, VetUpdateRequestDto requestDto){
        Vet vet = vetRepository.findByUserId(userId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 유저입니다."));

        vet.update(requestDto);
    }
}
