package dev.jooz.Web.domain.post;

import dev.jooz.Web.domain.image.ImageDto;
import dev.jooz.Web.domain.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    public Post save(PostDto.CreateReq dto){
        return postRepository.save(dto.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<PostDto.PostRes> findAll(Pageable pageable){

        Page<PostDto.PostRes> pages=postRepository.findAll(pageable).map(post -> {
            ImageDto.ImageCreateDto image=imageService.findByPost(post);

            if(image==null)
                return new PostDto.PostRes(post,new ImageDto.ImageCreateDto("Default.png"));

            return new PostDto.PostRes(post, image);
        });

        return pages;
    }

    public PostDto.PostDetailRes findById(Long id){
        Optional<Post> post=postRepository.findById(id);

        post.orElseThrow(()-> new NoSuchElementException());

        List<ImageDto.ImageCreateDto> images=imageService.findAllByPost(post.get());
        PostDto.PostDetailRes postDetailRes=new PostDto.PostDetailRes(post.get(),images);

        return postDetailRes;
    }

    public PostDto.PostRes update(Long id,PostDto.UpdateReq dto){
        Optional<Post> optionalPost=postRepository.findById(id);
        optionalPost.orElseThrow(()-> new NoSuchElementException());
        Post post=optionalPost.get();

        post.updatePost(dto);

        return new PostDto.PostRes(post);
    }

    public void delete(Long id){
        Optional<Post> optionalPost=postRepository.findById(id);
        optionalPost.orElseThrow(()-> new NoSuchElementException());
        Post post=optionalPost.get();

        postRepository.delete(post);
    }
}
