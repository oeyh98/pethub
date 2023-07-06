package ium.pethub.domain.repository;

import ium.pethub.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCallNumber(String callNumber);
    @Query("select u from User u left join ChatRoom cr on (cr.owner.id = u.id or cr.invited.id = u.id) where cr.id = :roomId and not u.id = :userId")
    User findPartnerByRoomIdAndUserId(@Param("roomId") Long roomId, @Param("userId") Long userId);

}
