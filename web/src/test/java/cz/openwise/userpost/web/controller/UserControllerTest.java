package cz.openwise.userpost.web.controller;

import java.util.List;

import cz.openwise.userpost.core.domain.UserWithPostsDO;
import cz.openwise.userpost.core.entity.Post;
import cz.openwise.userpost.core.entity.User;
import cz.openwise.userpost.core.repository.PostRepository;
import cz.openwise.userpost.core.repository.UserRepository;
import cz.openwise.userpost.web.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Test suit for {@link UserController}.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends AbstractControllerTest {

    private static final long USER_ID = 1L;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void getUserWithPosts_success() throws Exception {
        final User mockUser = readSource("mock-user.json", User.class);
        final List<Post> mockPosts = readSourceAsList("mock-posts.json", Post.class);
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Mono.just(mockUser));
        Mockito.when(postRepository.findByUserId(USER_ID)).thenReturn(Flux.fromIterable(mockPosts));

        final UserWithPostsDO expected = readSource("expected-userpost.json", UserWithPostsDO.class);
        webTestClient
                .get().uri("/users/{id}", USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserWithPostsDO.class)
                .isEqualTo(expected);
    }


}
