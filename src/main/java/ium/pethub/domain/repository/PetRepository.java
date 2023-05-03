package ium.pethub.domain.repository;

import ium.pethub.domain.entity.Pet;
import ium.pethub.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
