package dev.jooz.Web.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

}
