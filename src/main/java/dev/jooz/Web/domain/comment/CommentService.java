package dev.jooz.Web.domain.comment;

import dev.jooz.Web.domain.comment.CommentRepository;
import dev.jooz.Web.domain.post.Post;
import dev.jooz.Web.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentDto.CommentRes save(CommentDto.CreateReq dto){
        Comment comment=commentRepository.save(dto.toEntity());

        return new CommentDto.CommentRes(comment);
    }

    public void delete(Long commentId){
        Optional<Comment> commentOptional=commentRepository.findById(commentId);
        commentOptional.orElseThrow(()-> new NoSuchElementException());
        Comment comment=commentOptional.get();

        commentRepository.delete(comment);
    }

    public List<CommentDto.CommentRes> findAll(Long postId){
        Optional<Post> postOptional=postRepository.findById(postId);
        postOptional.orElseThrow(()->new NoSuchElementException());
        Post post=postOptional.get();

        List<Comment> commentList=commentRepository.findByPost(post);

        List<CommentDto.CommentRes> commentResList=new ArrayList<>();

        for(int i=0;i<commentList.size();i++)
            commentResList.add(new CommentDto.CommentRes(commentList.get(i)));
        return commentResList;
    }


}
