package ium.pethub.domain.entity;

import ium.pethub.dto.post.request.PostUpdateRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private String thumbnail;

    @Builder
    public Post(User user, String title, String content, String thumbnail) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public void update(PostUpdateRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.thumbnail= requestDto.getThumbnail();
    }
}
