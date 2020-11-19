package dev.jooz.Web.domain.image;

import dev.jooz.Web.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {

    public List<Image> findByPost(Post post);

}
