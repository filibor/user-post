package cz.openwise.userpost.integration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Parent of all integration tests
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    private final ObjectMapper objectMapper;

    public AbstractIntegrationTest() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Gets the path to directory with resources for this test.
     *
     * @return default resource base
     */
    protected String getResourcesBase() {
        return getClass().getName() + File.separator;
    }

    /**
     * Read JSON resource from file with given filename (relative to resource base ) and deserialize it to the target class.
     *
     * @param filename    relative filename to resource base
     * @param targetClass target class of loaded JSON data
     * @param <T>         type of JSON data
     * @return loaded resource and deserialized to target object
     */
    protected <T> T readSource(String filename, Class<T> targetClass) throws Exception {
        String jsonString = getJsonString(filename);
        return objectMapper.readValue(jsonString, targetClass);
    }

    /**
     * Read JSON resource from file with given filename (relative to resource base ) and deserialize it to the target List@lt;class@gt;.
     *
     * @param filename    relative filename to resource base
     * @param targetClass target class of loaded JSON data
     * @param <T>         type of JSON data
     * @return loaded resource and deserialized to target list of objects
     */
    protected  <T> List<T> readSourceAsList(String filename, Class<T> targetClass) throws Exception {
        String jsonString = getJsonString(filename);
        return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, targetClass));
    }

    private String getJsonString(final String filename) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource(getResourcesBase() + filename).toURI());
        return Files.lines(path).collect(Collectors.joining("\n"));
    }


}
