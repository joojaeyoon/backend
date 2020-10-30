package dev.jooz.Web.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostDto.PostRes createAccount(@RequestBody @Valid final PostDto.CreateReq dto){
        return new PostDto.PostRes(postService.save(dto));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Page<PostDto.PostRes> getPostList(final PageRequest pageable){
        return postService.findAll(pageable.of()).map(PostDto.PostRes::new);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostDto.PostRes updatePost(@PathVariable("id") Long id,@RequestBody @Valid final PostDto.UpdateReq dto){
        return postService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deletePost(@PathVariable Long id){
        return postService.delete(id);
    }
}
