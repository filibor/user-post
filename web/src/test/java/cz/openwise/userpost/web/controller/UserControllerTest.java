package cz.openwise.userpost.web.controller;

import cz.openwise.userpost.core.domain.ImmutablePostDO;
import cz.openwise.userpost.core.domain.ImmutableUserWithPostsDO;
import cz.openwise.userpost.core.domain.UserWithPostsDO;
import cz.openwise.userpost.core.service.UserPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * Test suit for {@link UserController}.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private static final long USER_ID = 1L;
    private static final long NON_EXISTING_USER_ID = 20L;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserPostService userPostService;

    @Test
    public void getUserWithPosts_success() {
        UserWithPostsDO userWithPostsDO = getUserWithPosts();
        Mockito.when(userPostService.getUserWithPosts(USER_ID)).thenReturn(Mono.just(userWithPostsDO));

        webTestClient
                .get().uri("/users/{id}", USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserWithPostsDO.class)
                .isEqualTo(userWithPostsDO);
    }

    @Test
    public void getUserWithPosts_notFound() {
        Mockito.when(userPostService.getUserWithPosts(NON_EXISTING_USER_ID)).thenThrow(WebClientResponseException.NotFound.class);
        webTestClient
                .get().uri("/users/{id}", NON_EXISTING_USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    private UserWithPostsDO getUserWithPosts() {
        return ImmutableUserWithPostsDO.builder()
                                       .name("Filip")
                                       .username("filibor")
                                       .email("emai@email.cz")
                                       .addPosts(ImmutablePostDO.builder()
                                                                .id(1)
                                                                .title("Some title")
                                                                .build())
                                       .build();
    }
}
