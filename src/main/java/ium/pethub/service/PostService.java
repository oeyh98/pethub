package ium.pethub.service;

import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.Post;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.OwnerRepository;
import ium.pethub.domain.repository.PostRepository;
import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.post.request.PostSaveRequestDto;
import ium.pethub.dto.post.request.PostUpdateRequestDto;
import ium.pethub.dto.post.response.PostListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final OwnerRepository ownerRepository;

    @Transactional
    public Long savePost(Long userId, PostSaveRequestDto requestDto) {
        Owner owner = ownerRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("회원이 존재하지 않습니다. id=" + userId));

        Post post = requestDto.toEntity(owner);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public Page<PostListResponseDto> getAllPosts(int page){
        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdAt").descending());
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostListResponseDto> postList = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(postList, pageable, posts.getTotalElements());
    }

    //User -> Owner -> Post ???? 뭐이리 돌아가 이게 맞아?
    @Transactional(readOnly = true)
    public Page<PostListResponseDto> findPostsByUserId(Long userId, int page){
        Owner owner = ownerRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("회원이 존재하지 않습니다. id=" + userId));

        return getPostListResponseDto(owner.getId(), page);
    }

    // 조훈창 - 수정
    // Owner에 nickname 없음
    // @Transactional(readOnly = true)
    // public Page<PostListResponseDto> findPostsByNickname(String nickname, int page){
    //     Owner owner = ownerRepository.findByNickname(nickname).orElseThrow(
    //             () -> new EntityNotFoundException("회원이 존재하지 않습니다. nickname=" + nickname));

    //     return getPostListResponseDto(owner.getId(), page);
    // }

    @Transactional(readOnly = true)
    public Page<PostListResponseDto> getPostListResponseDto(Long ownerId, int page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdAt").descending());
        Page<Post> posts = postRepository.findAllByOwnerId(ownerId, pageable);
        List<PostListResponseDto> postList = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(postList, pageable, posts.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다. id=" +  postId));
        return post;
    }

    @Transactional
    public void updatePost(Long userId, Long postId, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(
                        ()-> new EntityNotFoundException("게시물이 존재하지 않습니다. id=" + postId));
        Owner owner = ownerRepository.findByUserId(userId).get();
        checkPostAuthor(post, owner.getId());

        post.update(requestDto);
    }

    @Transactional
    public void deletePost(long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(
                        () -> new EntityNotFoundException("게시물이 존재하지 않습니다. id=" + postId)
                );
        Owner owner = ownerRepository.findByUserId(userId).get();
        checkPostAuthor(post, owner.getId());
        postRepository.delete(post);
    }

    void checkPostAuthor(Post post, Long ownerId) {
        if(post.getOwner().getId() != ownerId) {
            throw new IllegalStateException("게시물 작성자만 수정, 삭제가 가능합니다.");
        }
    }
}
