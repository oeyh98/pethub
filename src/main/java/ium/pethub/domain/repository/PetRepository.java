package ium.pethub.domain.repository;

import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByOwner(Owner owner);
}
