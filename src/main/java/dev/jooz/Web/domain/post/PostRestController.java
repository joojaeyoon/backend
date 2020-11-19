package dev.jooz.Web.domain.post;

import dev.jooz.Web.domain.comment.CommentDto;
import dev.jooz.Web.domain.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostDto.PostRes createPost(@RequestBody @Valid final PostDto.CreateReq dto){
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
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity deletePost(@PathVariable Long id){
        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{postId}/comment")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommentDto.CommentRes> getCommentList(@PathVariable Long postId) {
        return commentService.findAll(postId);
    }

    // TODO Post Img Upload Controller

}
