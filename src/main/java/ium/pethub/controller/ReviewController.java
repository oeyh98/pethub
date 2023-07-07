package ium.pethub.controller;

import ium.pethub.dto.review.ReviewSaveRequestDto;
import ium.pethub.dto.review.ReviewUpdateRequestDto;
import ium.pethub.service.ReviewService;
import ium.pethub.util.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/api/vet/{vetId}/review")
    public ResponseEntity<?> addReview(@PathVariable Long vetId, ReviewSaveRequestDto requestDto) throws Exception {
        Long ownerId = UserContext.userData.get().getUserId();
        Long reviewId = reviewService.addReview(ownerId, vetId, requestDto);
        return ResponseEntity.ok().body(reviewId);
    }

    @PutMapping("/api/vet/{vetId}/review/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long vetId, @PathVariable Long reviewId, ReviewUpdateRequestDto requestDto) throws Exception {
        //TODO:검증
        Long ownerId = UserContext.userData.get().getUserId();
        reviewService.updateReview(vetId, requestDto);
        return ResponseEntity.ok().body("리뷰 수정이 완료되었습니다.");
    }

    @DeleteMapping("/api/vet/{vetId}/review/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long vetId, @PathVariable Long reviewId) throws Exception {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().body("리뷰 삭제가 완료되었습니다.");
    }
}
