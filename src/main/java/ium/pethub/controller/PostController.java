package ium.pethub.controller;

import ium.pethub.domain.entity.Post;
import ium.pethub.dto.common.ResponseDto;
import ium.pethub.dto.post.request.PostSaveRequestDto;
import ium.pethub.dto.post.request.PostUpdateRequestDto;
import ium.pethub.dto.post.response.PostListResponseDto;
import ium.pethub.dto.post.response.PostResponseDto;
import ium.pethub.service.PostService;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @ValidToken
    @PostMapping
    public ResponseEntity savePost(@RequestBody PostSaveRequestDto requestDto) {
        Long userId = UserContext.userData.get().getUserId();
        Long postId = postService.savePost(userId, requestDto);
        return ResponseEntity.ok().body(ResponseDto.of("게시물 작성이 완료되었습니다.",postId));
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

    @ValidToken
    @GetMapping("/posts/{userId}/{page}")
    public ResponseEntity<?> findPostsByUserId(@PathVariable Long userId, @PathVariable int page){
        Page<PostListResponseDto> postList = postService.findPostsByUserId(userId, page);

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postList));
    }

    @GetMapping("/posts/nickname/{nickname}/{page}")
    public ResponseEntity<?> findPostsByNickname(@PathVariable String nickname, @PathVariable int page){
        Page<PostListResponseDto> postList = postService.findPostsByNickname(nickname, page);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postList));
    }

    //TODO: 아이디 검증
    @ValidToken
    @PutMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto requestDto) {
        postService.updatePost(postId, requestDto);
        return ResponseEntity.ok().body(ResponseDto.of("게시물 수정이 완료되었습니다."));
    }

    //TODO: 아이디 검증
    @ValidToken
    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return ResponseEntity.ok().body(ResponseDto.of("게시물 삭제가 완료되었습니다."));
    }
}

