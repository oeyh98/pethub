package ium.pethub.domain.entity;

import ium.pethub.dto.comment.request.CommentUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Table(name = "comment")
public class Comment extends BaseTimeEntity{

    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id", nullable = false)
    Vet vet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder
    public Comment(String content, Post post, Vet vet, Comment comment){
        this.content = content;
        this.post = post;
        this.vet = vet;
        this.parent = comment;
    }

    public void update(CommentUpdateRequestDto requestDto){
        this.content = requestDto.getContent();
    }
}
