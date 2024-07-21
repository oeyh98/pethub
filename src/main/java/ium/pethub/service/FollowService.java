package ium.pethub.service;


import ium.pethub.domain.entity.Follow;
import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.Vet;
import ium.pethub.domain.repository.FollowRepository;
import ium.pethub.domain.repository.OwnerRepository;
import ium.pethub.domain.repository.VetRepository;
import ium.pethub.dto.owner.response.OwnerFollowListResponseDto;
import ium.pethub.dto.owner.response.OwnerFollowResponseDto;
import ium.pethub.dto.owner.response.OwnerResponseDto;
import ium.pethub.dto.vet.response.VetFollowListResponseDto;
import ium.pethub.dto.vet.response.VetResponseDto;
import ium.pethub.exception.AlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;


    public OwnerFollowResponseDto saveFollow(Long userId, Long vetId) {
        Owner fromFollow = ownerRepository.findByUserId(userId).get();
        Vet toFollow = vetRepository.findById(vetId)
                .orElseThrow(()->new EntityNotFoundException("존재하지않는 유저입니다."));

        if(isFollow(fromFollow, toFollow))
            throw new AlreadyExistException("이미 구독중입니다.");

        Follow follow = Follow.builder()
                .fromFollow(fromFollow)
                .toFollow(toFollow)
                        .build();
        followRepository.save(follow);

        OwnerFollowResponseDto responseDto = OwnerFollowResponseDto.builder()
                .ownerId(fromFollow.getId())
                .vetId(vetId)
                .isFollow(true)
                .ownerFollowingCnt(followRepository.countByOwner(fromFollow))
                .vetFollowerCnt(followRepository.countByVet(toFollow))
                        .build();
        return responseDto;
    }

    public OwnerFollowResponseDto deleteFollow(Long ownerId, Long vetId) {
        Owner fromFollow = ownerRepository.findById(ownerId).get();
        Vet toFollow = vetRepository.findById(vetId)
                .orElseThrow(()->new EntityNotFoundException("존재하지않는 유저입니다."));
        if (!isFollow(fromFollow, toFollow)) {
            new IllegalStateException("구독중이 아닙니다.");
        }

        Follow follow = followRepository.findByOwnerAndVet(fromFollow, toFollow);
        followRepository.delete(follow);

        OwnerFollowResponseDto responseDto = OwnerFollowResponseDto.builder()
                .ownerId(ownerId)
                .vetId(vetId)
                .isFollow(false)
                .ownerFollowingCnt(followRepository.countByOwner(fromFollow))
                .vetFollowerCnt(followRepository.countByVet(toFollow))
                .build();
        return responseDto;
    }

    public boolean isFollow(Owner fromFollow, Vet toFollow) {
        return followRepository.existsByOwnerAndVet(fromFollow, toFollow);
    }

    public List<VetFollowListResponseDto> getFollowerListByVetId(Long vetId) {
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(()->new EntityNotFoundException("존재하지않는 유저입니다."));
        List<Follow> followerList = followRepository.findAllByVet(vet);
        List<VetFollowListResponseDto> followingList = followerList.stream()
                .map(follow -> {
                    Owner owner = follow.getOwner();
                    int vetFollowerCnt = followRepository.countByVet(vet);
                    return VetFollowListResponseDto.builder()
                            .userId(owner.getUser().getId())
                            .vetFollowerCnt(vetFollowerCnt)
                            .ownerInfo(new OwnerResponseDto(owner))
                            .build();
                })
                .collect(Collectors.toList());

        return followingList;
    }

    public List<OwnerFollowListResponseDto> getFollowingListByOwnerId(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(()->new EntityNotFoundException("존재하지않는 유저입니다."));
        List<Follow> followerList = followRepository.findAllByOwner(owner);
        List<OwnerFollowListResponseDto> followingList = followerList.stream()
                .map(follow -> {
                    Vet vet = follow.getVet();
                    int ownerFollowingCnt = followRepository.countByOwner(owner);
                    return OwnerFollowListResponseDto.builder()
                            .userId(vet.getUser().getId())
                            .ownerFollowingCnt(ownerFollowingCnt)
                            .vetInfo(new VetResponseDto(vet))
                            .build();
                })
                .collect(Collectors.toList());
        return followingList;
    }
}

//    public int countFollowing (Long userId) {
//        return followRepository.countByFromUserId(userId);
//    }
//
//    public int countFollower (Long userId) {
//        return followRepository.countByToUserId(userId);
//    }
//
//    public int countFollowing (String nickname) {
//        User user = userRepository.findByNickname(nickname)
//                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
//        return followRepository.countByFromUserId(user.getId());
//    }
//
//    public int countFollower (String nickname) {
//        User user = userRepository.findByNickname(nickname)
//                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
//        return followRepository.countByToUserId(user.getId());
//    }
//
//    public List<UserFollowListResponseDto> findFollowingListByUserId(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
//        List<UserFollowListResponseDto> followingList = followRepository.findAllByFromUser(user);
//        return followingList;
//    }
//
//    public List<UserFollowListResponseDto> findFollowerListByUserId(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
//        List<UserFollowListResponseDto> followerList = followRepository.findAllByFromUser(user);
//        return followerList;
//    }
