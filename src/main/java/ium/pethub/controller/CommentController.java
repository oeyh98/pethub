package ium.pethub.controller;

import ium.pethub.dto.comment.request.CommentSaveRequestDto;
import ium.pethub.dto.comment.request.CommentUpdateRequestDto;
import ium.pethub.dto.common.ResponseDto;
import ium.pethub.service.CommentService;
import ium.pethub.util.AuthCheck;
import ium.pethub.util.AuthenticationPrincipal;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // requstBody
    @AuthCheck(role = AuthCheck.Role.VET)
    @PostMapping("api/post/{postId}/comment")
    public ResponseEntity saveComment(@AuthenticationPrincipal Long userId, @PathVariable Long postId, @RequestBody CommentSaveRequestDto requestDto) {
        Long commentId = commentService.saveComment(userId, postId, requestDto);
        return ResponseEntity.ok().body(ResponseDto.of("댓글 작성이 완료되었습니다.", Map.of("commentId", commentId)));
    }

    @AuthCheck(role = AuthCheck.Role.VET)
    @PutMapping("api/post/{postId}/comment/{commentId}")
    public ResponseEntity updateComment(@AuthenticationPrincipal Long userId, @PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentUpdateRequestDto requestDto) {
        commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok().body(ResponseDto.of("댓글 수정이 완료되었습니다."));
    }


    @AuthCheck(role = AuthCheck.Role.VET)
    @DeleteMapping("api/post/{postId}/comment/{commentId}")
    public ResponseEntity deleteComment(@AuthenticationPrincipal Long userId, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body(ResponseDto.of("댓글 삭제가 완료되었습니다."));
    }
}
