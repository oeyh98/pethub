package ium.pethub.service;

import ium.pethub.domain.entity.Owner;
import ium.pethub.domain.entity.Review;
import ium.pethub.domain.entity.Vet;
import ium.pethub.domain.repository.OwnerRepository;
import ium.pethub.domain.repository.ReviewRepository;
import ium.pethub.domain.repository.VetRepository;
import ium.pethub.dto.review.ReviewSaveRequestDto;
import ium.pethub.dto.review.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final VetRepository vetRepository;
    private final OwnerRepository ownerRepository;

    @Transactional
    public Long addReview(Long ownerId, Long vetId, ReviewSaveRequestDto requestDto) throws Exception {
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() -> new Exception("존재하지 않는 수의사입니다."));
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new Exception("존재하지 않는 유저입니다."));
        Review review = requestDto.toEntity(owner, vet);
        reviewRepository.save(review);
        updateRatingForVet(vet);
        return review.getId();
    }

    @Transactional
    public void updateReview(Long vetId, Long reviewId, ReviewUpdateRequestDto requestDto) throws Exception {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception("존재하지 않는 리뷰입니다."));
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() -> new Exception("존재하지 않는 수의사입니다."));
        review.update(requestDto);
        updateRatingForVet(vet);
    }

    @Transactional
    public void deleteReview(Long vetId, Long reviewId) throws Exception {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception("존재하지 않는 리뷰입니다."));
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() -> new Exception("존재하지 않는 수의사입니다."));

        reviewRepository.delete(review);
        updateRatingForVet(vet);
    }
    @Transactional
    public void updateRatingForVet(Vet vet) throws Exception {
        List<Review> reviewList = vet.getReviewList();
        if (reviewList.isEmpty()) {
            vet.updateRating(0);
        } else {
            int totalRating = reviewList.stream().mapToInt(Review::getRating).sum();
            int newRating = totalRating / reviewList.size();

            vet.updateRating(newRating);
        }
    }
}
