package ium.pethub.domain.repository;

import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.Pet;
import ium.pethub.domain.entity.User;
import ium.pethub.dto.pet.response.PetListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByOwner(Owner owner);
}
