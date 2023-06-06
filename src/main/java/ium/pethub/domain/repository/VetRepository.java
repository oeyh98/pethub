package ium.pethub.domain.repository;

import ium.pethub.domain.entity.Vet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VetRepository extends JpaRepository<Vet, Long> {
    Optional<Vet> findByUserId(Long userId);
}
