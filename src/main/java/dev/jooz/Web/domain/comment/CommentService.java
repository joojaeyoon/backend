package dev.jooz.Web.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Long save(CommentRequestDto requestDto){
        return commentRepository.save(requestDto.toEntity()).getId();
    }
}
