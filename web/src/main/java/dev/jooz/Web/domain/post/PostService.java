package dev.jooz.Web.domain.post;

import dev.jooz.Web.domain.account.Account;
import dev.jooz.Web.domain.account.AccountService;
import dev.jooz.Web.domain.image.ImageDto;
import dev.jooz.Web.domain.image.ImageService;
import dev.jooz.Web.exception.account.InvalidTokenException;
import dev.jooz.Web.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AccountService accountService;
    private final ImageService imageService;
    private final JwtUtil jwtUtil;

    private final String path="src/main/resources/static/images/";


    public PostDto.PostDetailRes save(PostDto.CreateReq dto,String token){

        try {
            String username = jwtUtil.getUsername(token);

            if (!jwtUtil.validateToken(token, username))
                throw new Exception();

            Account account=accountService.findByUsername(username);
            dto.setAccount(account);
        }
        catch (Exception e){
            throw new InvalidTokenException(token);
        }

        dto.setCompleted(false);

        Post post=postRepository.save(dto.toEntity());
        File dir=new File(path+post.getId());
        dir.mkdir();

//        if (dto.getImages()!=null)
//            imageService.save(dto.getImages(), post);

        return new PostDto.PostDetailRes(post,dto.getImages());
    }

    @Transactional(readOnly = true)
    public Page<PostDto.PostRes> findAll(final PageRequest pageRequest){
        return postRepository.findAll(pageRequest.of()).map(post -> {
            ImageDto.ImageCreateDto image=imageService.findFirstByPost(post);

            if(image==null)
                return new PostDto.PostRes(post,new ImageDto.ImageCreateDto("Default.png"));

            return new PostDto.PostRes(post, image);
        });
    }

    public PostDto.PostDetailRes findById(Long id){
        Optional<Post> post=postRepository.findById(id);

        post.orElseThrow(()-> new NoSuchElementException());

        List<ImageDto.ImageCreateDto> images=imageService.findAllByPost(post.get());
        return new PostDto.PostDetailRes(post.get(),images);
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
