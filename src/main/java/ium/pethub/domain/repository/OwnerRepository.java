package ium.pethub.domain.repository;

import ium.pethub.domain.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    // 조훈창 - 수정
    // Owner에 nickname 없음
    // Optional<Owner> findByNickname(String nickname);

    Optional<Owner> findByUserId(Long userId);

    // boolean existsByNickname(String nickname);

}
