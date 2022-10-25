package sqltask.helpers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CustomFileReader {

    public Stream<String> readFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(fileName);
        try {
            if (url != null) {
                return Files.lines(Path.of(url.toURI()));
            } else {
                throw new IllegalStateException("This URL is incorrect");
            }
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
}
