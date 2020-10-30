package dev.jooz.Web.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post save(PostDto.CreateReq dto){
        return postRepository.save(dto.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public PostDto.PostRes update(Long id,PostDto.UpdateReq dto){
        Optional<Post> optionalPost=postRepository.findById(id);
        optionalPost.orElseThrow(()-> new NoSuchElementException());
        Post post=optionalPost.get();

        System.out.println(post);
        post.updatePost(dto);

        System.out.println("---------------------------------------");
        System.out.println(post);
        System.out.println("---------------------------------------");

        return new PostDto.PostRes(post);
    }
}
