package ium.pethub.controller;

import ium.pethub.domain.entity.Post;
import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.post.request.PostSaveRequestDto;
import ium.pethub.dto.post.request.PostUpdateRequestDto;
import ium.pethub.dto.post.response.PostListResponseDto;
import ium.pethub.dto.post.response.PostResponseDto;
import ium.pethub.service.PostService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    //TODO: post 분리
    //TODO: 게시물 목록에서 댓글 수 반환
    @AuthCheck(role = AuthCheck.Role.OWNER)
    @PostMapping
    public ResponseEntity savePost(@AuthenticationPrincipal Long userId, @RequestBody PostSaveRequestDto requestDto) {
        Long postId = postService.savePost(userId, requestDto);
        return ResponseEntity.ok().body(ResponseDto.of("게시물 작성이 완료되었습니다.", Map.of("postId", postId)));
    }

    @GetMapping("/posts/{page}")
    public ResponseEntity<?> getAllPosts(@PathVariable int page) {
        Page<PostListResponseDto> postList = postService.getAllPosts(page);
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        PostResponseDto responseDto = new PostResponseDto(post);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/posts/{userId}/{page}")
    public ResponseEntity<?> findPostsByUserId(@PathVariable Long userId, @PathVariable int page){
        Page<PostListResponseDto> postList = postService.findPostsByUserId(userId, page);

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postList));
    }

    // 조훈창 - 수정
    // Owner에 nickname 없음
    // @GetMapping("/posts/nickname/{nickname}/{page}")
    // public ResponseEntity<?> findPostsByNickname(@PathVariable String nickname, @PathVariable int page){
    //     Page<PostListResponseDto> postList = postService.findPostsByNickname(nickname, page);
    //     return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postList));
    // }

    @PutMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@AuthenticationPrincipal Long userId, @PathVariable Long postId, @RequestBody PostUpdateRequestDto requestDto) {
        postService.updatePost(userId, postId, requestDto);
        return ResponseEntity.ok().body(ResponseDto.of("게시물 수정이 완료되었습니다."));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@AuthenticationPrincipal Long userId, @PathVariable Long postId){
        postService.deletePost(userId, postId);
        return ResponseEntity.ok().body(ResponseDto.of("게시물 삭제가 완료되었습니다."));
    }
}

