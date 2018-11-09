package cz.openwise.userpost.core.service;

import cz.openwise.userpost.core.domain.ImmutablePostDO;
import cz.openwise.userpost.core.domain.ImmutableUserWithPostsDO;
import cz.openwise.userpost.core.domain.UserWithPostsDO;
import cz.openwise.userpost.core.repository.PostRepository;
import cz.openwise.userpost.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Aggregator service for Users and their Posts.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@Service
public class UserPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieve aggregated information about User and all their Posts.
     *
     * @param userId id of User
     * @return User with their Posts
     */
    public Mono<UserWithPostsDO> getUserWithPosts(long userId) {
        return Mono.zip(
                userRepository.findById(userId)
                              .map(user -> ImmutableUserWithPostsDO.builder()
                                                                   .name(user.getName())
                                                                   .username(user.getUsername())
                                                                   .email(user.getEmail())
                                                                   .build()),
                postRepository.findByUserId(userId)
                              .map(postEntity -> ImmutablePostDO.builder()
                                                                .id(postEntity.getId())
                                                                .title(postEntity.getTitle())
                                                                .build())
                              .collectList(),
                ImmutableUserWithPostsDO::withPosts

        );
    }
}
