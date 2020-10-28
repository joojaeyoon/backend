package dev.jooz.Web.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post save(PostDto.CreateReq dto){
        return postRepository.save(dto.toEntity());
    }


}
