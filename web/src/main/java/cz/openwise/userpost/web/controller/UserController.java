package cz.openwise.userpost.web.controller;

import cz.openwise.userpost.core.domain.UserWithPostsDO;
import cz.openwise.userpost.core.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller for Users.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserPostService service;

    /**
     * Get User and his posts.
     * @param userId id of User
     * @return User with hos posts
     */
    @GetMapping("/{userId}")
    public Mono<UserWithPostsDO> getUserWithPosts(@PathVariable long userId) {
        return service.getUserWithPosts(userId);
    }
}
