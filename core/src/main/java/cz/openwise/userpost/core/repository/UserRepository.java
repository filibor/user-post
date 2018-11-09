package cz.openwise.userpost.core.repository;

import cz.openwise.userpost.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Repository for {@link User}.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@Repository
public class UserRepository {

    @Autowired
    private WebClient webClient;

    /**
     * Find User identified by id.
     * @param userId if of User
     * @return User
     */
    @Cacheable("users")
    public Mono<User> findById(long userId) {
        return webClient
                .method(HttpMethod.GET)
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(User.class)
                .cache();

    }

}
