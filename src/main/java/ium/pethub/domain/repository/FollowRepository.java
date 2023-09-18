package ium.pethub.domain.repository;

import ium.pethub.domain.entity.Follow;
import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {


    int countByVet(Vet vet); // 팔로워 수 (follower)
    int countByOwner(Owner owner);// 팔로우 수 (following)

    boolean existsByOwnerAndVet(Owner owner, Vet vet);
    Follow findByOwnerAndVet(Owner owner, Vet vet);
    List<Follow> findAllByVet(Vet vet);
    List<Follow> findAllByOwner(Owner owner);
}
