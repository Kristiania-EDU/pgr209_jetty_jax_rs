package no.kristiania.edu;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LibraryServerTests {
    @Test
    void shouldServeHomePage() throws IOException {
        var server = new LibraryServer(0);
        URL url = server.getUrl();

        var urlConnection = (HttpURLConnection) url.openConnection();
        var responseCode = urlConnection.getResponseCode();

        assertThat(responseCode)
            .as(urlConnection.getResponseMessage() + " for " + url )
            .isEqualTo(200);

        assertThat(urlConnection.getInputStream())
            .asString(StandardCharsets.UTF_8)
            .contains("<h1>My Library</h1>");
    }
}
