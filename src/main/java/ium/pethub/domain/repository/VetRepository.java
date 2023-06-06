package ium.pethub.domain.repository;

import ium.pethub.domain.entity.Vet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Long> {
}
