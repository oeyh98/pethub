package ium.pethub.domain.repository;

import ium.pethub.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUserId(Long userId, Pageable pageable);

    Page<Post> findAllByUser_Nickname(String nickname, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
