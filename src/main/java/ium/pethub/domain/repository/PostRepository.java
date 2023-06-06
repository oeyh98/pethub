package ium.pethub.domain.repository;

import ium.pethub.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOwnerId(Long ownerId, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
