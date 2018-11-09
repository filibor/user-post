package cz.openwise.userpost.core.repository.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration of WebClient for User posts master system.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                        .baseUrl("https://jsonplaceholder.typicode.com")
                        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .build();
    }

}
