package ium.pethub.controller;

import ium.pethub.dto.comment.request.CommentSaveRequestDto;
import ium.pethub.dto.comment.request.CommentUpdateRequestDto;
import ium.pethub.dto.common.ResponseDto;
import ium.pethub.service.CommentService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //requstBody
    @ValidToken
    @AuthCheck(role = AuthCheck.Role.VET)
    @PostMapping("api/post/{postId}/comment")
    public ResponseEntity saveComment(@PathVariable Long postId, @RequestBody CommentSaveRequestDto requestDto) {
        Long userId = UserContext.userData.get().getUserId();
        Long commentId = commentService.saveComment(userId, postId, requestDto);
        return ResponseEntity.ok().body(ResponseDto.of("댓글 작성이 완료되었습니다.", Map.of("commentId", commentId)));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.VET)
    @PutMapping("api/post/{postId}/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentUpdateRequestDto requestDto) {
        Long userId = UserContext.userData.get().getUserId();
        commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok().body(ResponseDto.of("댓글 수정이 완료되었습니다."));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.VET)
    @DeleteMapping("api/post/{postId}/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        Long userId = UserContext.userData.get().getUserId();
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body(ResponseDto.of("댓글 삭제가 완료되었습니다."));
    }
}
