package cz.openwise.userpost.integration;

import cz.openwise.userpost.core.domain.UserWithPostsDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Test suit for User post service.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
public class UserPostIntegrationTest extends AbstractIntegrationTest {

    private static final long USER_ID = 1L;
    private static final long NON_EXISTING_USER_ID = 20L;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getUserWithPosts_success() throws Exception {
        final UserWithPostsDO expected = readSource("expected-userpost.json", UserWithPostsDO.class);
        webTestClient
                .get().uri("/users/{id}", USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserWithPostsDO.class)
                .isEqualTo(expected);
    }

    @Test
    public void getUserWithPosts_notFound() {
        webTestClient
                .get().uri("/users/{id}", NON_EXISTING_USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }


}
