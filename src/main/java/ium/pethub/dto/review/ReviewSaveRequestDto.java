//package ium.pethub.dto.review;
//
//import ium.pethub.domain.entity.Owner;
//import ium.pethub.domain.entity.Review;
//import ium.pethub.domain.entity.Vet;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//public class ReviewSaveRequestDto {
//    private Long vetId;
//    private String content;
//    private int rating;
//
//    public Review toEntity(Owner owner, Vet vet){
//        return Review.builder()
//                .owner(owner)
//                .vet(vet)
//                .content(content)
//                .rating(rating)
//                .build();
//    }
//}
