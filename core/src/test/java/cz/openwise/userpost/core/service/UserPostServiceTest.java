package cz.openwise.userpost.core.service;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import cz.openwise.userpost.core.ServiceTestConfiguration;
import cz.openwise.userpost.core.domain.UserWithPostsDO;
import cz.openwise.userpost.core.entity.ImmutablePost;
import cz.openwise.userpost.core.entity.ImmutableUser;
import cz.openwise.userpost.core.entity.Post;
import cz.openwise.userpost.core.entity.User;
import cz.openwise.userpost.core.repository.PostRepository;
import cz.openwise.userpost.core.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Test suit for {@link UserPostService}.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTestConfiguration.class)
public class UserPostServiceTest {

    private static final String NAME = "Filip";
    private static final String EMAIL = "email@email.cz";
    private static final String USERNAME = "Borda";
    private static final String TITLE_1 = "Title1";
    private static final String TITLE_2 = "Title2";
    private static final long ID_1 = 2L;
    private static final long ID_2 = 3L;
    private static final long USER_ID = 1L;

    @Autowired
    private UserPostService service;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void getUserWithPosts_success() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Mono.just(getUser()));
        Mockito.when(postRepository.findByUserId(USER_ID)).thenReturn(Flux.fromIterable(getPosts()));
        final UserWithPostsDO userPosts = service.getUserWithPosts(USER_ID).block();

        assertThat(userPosts.getName()).isEqualTo(NAME);
        assertThat(userPosts.getEmail()).isEqualTo(EMAIL);
        assertThat(userPosts.getUsername()).isEqualTo(USERNAME);
        Assertions.assertThat(userPosts.getPosts()).hasSize(2);
        assertThat(userPosts.getPosts().get(0).getId()).isEqualTo(ID_1);
        assertThat(userPosts.getPosts().get(0).getTitle()).isEqualTo(TITLE_1);
        assertThat(userPosts.getPosts().get(1).getId()).isEqualTo(ID_2);
        assertThat(userPosts.getPosts().get(1).getTitle()).isEqualTo(TITLE_2);
        Mockito.verify(userRepository).findById(USER_ID);
        Mockito.verify(postRepository).findByUserId(USER_ID);
        Mockito.verifyNoMoreInteractions(userRepository, postRepository);
    }

    private User getUser() {
        return ImmutableUser.builder()
                            .id(USER_ID)
                            .name(NAME)
                            .username(USERNAME)
                            .email(EMAIL)
                            .build();
    }

    private List<Post> getPosts() {
        return Arrays.asList(
                ImmutablePost.builder()
                             .id(ID_1)
                             .userId(USER_ID)
                             .title(TITLE_1)
                             .body("body1")
                             .build(),
                ImmutablePost.builder()
                             .id(ID_2)
                             .userId(USER_ID)
                             .title(TITLE_2)
                             .body("body2")
                             .build());
    }

}
