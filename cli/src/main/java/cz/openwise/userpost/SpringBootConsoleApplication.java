package cz.openwise.userpost;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.openwise.userpost.core.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Base command line initializer.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    private static final String WRONG_ARGUMENT_MESSAGE = "Program requires one numeric argument: userId";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserPostService userPostService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(final String... args) throws Exception {
        if (args.length < 1) {
            System.out.println(WRONG_ARGUMENT_MESSAGE);
            return;
        }
        final long userId;
        try {
            userId = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println(WRONG_ARGUMENT_MESSAGE);
            return;
        }

        objectMapper.writeValue(System.out, userPostService.getUserWithPosts(userId).block());
        System.exit(0);
    }
}
