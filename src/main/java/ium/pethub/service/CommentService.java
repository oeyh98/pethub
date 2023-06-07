package ium.pethub.service;

import ium.pethub.domain.entity.Comment;
import ium.pethub.domain.entity.Post;
import ium.pethub.domain.entity.Vet;
import ium.pethub.domain.repository.CommentRepository;
import ium.pethub.domain.repository.PostRepository;
import ium.pethub.domain.repository.VetRepository;
import ium.pethub.dto.comment.request.CommentSaveRequestDto;
import ium.pethub.dto.comment.request.CommentUpdateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final VetRepository vetRepository;
    private final PostRepository postRepository;
    @Transactional
    public long saveComment(Long userId, Long postId, CommentSaveRequestDto requestDto){
        Vet vet = vetRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."+ userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."+ postId));
        Comment comment = requestDto.toEntity(post, vet);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequestDto requestDto)
    {
        Comment comment = commentRepository.findById(commentId).get();
        comment.update(requestDto);
    }

    @Transactional
    public void deleteComment(Long commnetId){
        Comment comment = commentRepository.findById(commnetId).get();
        commentRepository.delete(comment);
    }
}
