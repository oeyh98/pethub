package ium.pethub.service;

import ium.pethub.domain.entity.Post;
import ium.pethub.domain.entity.User;
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

    private final PostRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long savePost(Long userId, PostSaveRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("회원이 존재하지 않습니다. id=" + userId));

        Post post = requestDto.toEntity(user);
        postsRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public Page<PostListResponseDto> getAllPosts(int page){
        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdAt").descending());
        Page<Post> posts = postsRepository.findAll(pageable);
        List<PostListResponseDto> postList = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(postList, pageable, posts.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<PostListResponseDto> findPostsByUserId(Long userId, int page){
        userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("회원이 존재하지 않습니다. id=" + userId));

        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdAt").descending());
        Page<Post> posts = postsRepository.findAllByUserId(userId, pageable);
        List<PostListResponseDto> postList = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(postList, pageable, posts.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<PostListResponseDto> findPostsByNickname(String nickname, int page){
        userRepository.findByNickname(nickname).orElseThrow(
                () -> new EntityNotFoundException("회원이 존재하지 않습니다. nickname=" + nickname));

        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdAt").descending());
        Page<Post> posts = postsRepository.findAllByUser_Nickname(nickname, pageable);
        List<PostListResponseDto> postList = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(postList, pageable, posts.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId){
        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다. id=" +  postId));
        return post;
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDto requestDto) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(
                        ()-> new EntityNotFoundException("게시물이 존재하지 않습니다. id=" + postId));

        post.update(requestDto);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(
                        () -> new EntityNotFoundException("게시물이 존재하지 않습니다. id=" + postId)
                );
        postsRepository.delete(post);
    }
}
