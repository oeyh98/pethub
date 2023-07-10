package ium.pethub.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {
    private Long vetId;
    private String content;
    private int rating;
}
