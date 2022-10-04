package no.kristiania.edu;

import org.junit.jupiter.api.Test;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryServerTests {
    @Test
    void shouldServeHomePage() throws Exception {
        var server = new LibraryServer(0);
        server.start();

        var urlConnection = (HttpURLConnection) server.getUrl().openConnection();
        var responseCode = urlConnection.getResponseCode();

        assertThat(responseCode)
            .as(urlConnection.getResponseMessage() + " for " + urlConnection.getURL() )
            .isEqualTo(200);

        assertThat(urlConnection.getInputStream())
            .asString(StandardCharsets.UTF_8)
            .contains("<h1>My Library</h1>");
    }
}
