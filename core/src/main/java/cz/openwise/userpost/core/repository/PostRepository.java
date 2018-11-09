package cz.openwise.userpost.core.repository;

import cz.openwise.userpost.core.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Repository for {@link Post}.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@Repository
public class PostRepository {

    @Autowired
    private WebClient webClient;

    /**
     * Find all posts posted by User identified by id.
     * @param userId id of User
     * @return All Posts posted by user
     */
    @Cacheable("posts")
    public Flux<Post> findByUserId(long userId) {
        return webClient
                .method(HttpMethod.GET)
                .uri("/posts?userId={id}", userId)
                .retrieve()
                .bodyToFlux(Post.class)
                .cache();

    }

}
