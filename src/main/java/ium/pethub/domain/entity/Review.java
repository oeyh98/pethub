//package ium.pethub.domain.entity;
//
//
//import ium.pethub.dto.review.ReviewUpdateRequestDto;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.lang.Nullable;
//
//import jakarta.persistence.*;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//public class Review {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, name = "review_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vet_id")
//    private Vet vet;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id",nullable = false)
//    private Owner owner;
//
//    @Column(nullable = false)
//    private String content;
//
//    @Column(nullable = false)
//    private int rating;
//
//    @Builder
//    public Review(Vet vet, Owner owner, String content, int rating) {
//        this.vet = vet;
//        this.owner = owner;
//        this.content = content;
//        this.rating = rating;
//    }
//
//    public void update(ReviewUpdateRequestDto requestDto){
//        this.content = requestDto.getContent();
//        this.rating = requestDto.getRating();
//    }
//}
