package dev.jooz.Web.domain.post;

import dev.jooz.Web.aws.S3Uploader;
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
    private final S3Uploader s3Uploader;

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

        if (dto.getImages()!=null)
            imageService.save(dto.getImages(), post);

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

    public void delete(Long id,String token){

        Optional<Post> optionalPost=postRepository.findById(id);
        optionalPost.orElseThrow(()-> new NoSuchElementException());
        Post post=optionalPost.get();
        String username=post.getAccount().getUsername();

        if(!jwtUtil.validateToken(token,username))
            throw new InvalidTokenException(token);

        List<ImageDto.ImageCreateDto> images=imageService.findAllByPost(post);

        for(ImageDto.ImageCreateDto image : images){
            s3Uploader.delete(image.getName());
        }

        postRepository.delete(post);
    }
}
