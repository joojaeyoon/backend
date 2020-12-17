package dev.jooz.Web.domain.comment;

import dev.jooz.Web.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    public List<Comment> findByPost(Post post);
}
