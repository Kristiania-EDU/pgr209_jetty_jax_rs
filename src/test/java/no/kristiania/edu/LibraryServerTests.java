package no.kristiania.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryServerTests {

    private LibraryServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new LibraryServer(0);
        server.start();
    }

    @Test
    void shouldServeHomePage() throws IOException {
        var urlConnection = getUrlConnection("/");
        var responseCode = urlConnection.getResponseCode();

        assertThat(responseCode)
            .as(urlConnection.getResponseMessage() + " for " + urlConnection.getURL() )
            .isEqualTo(200);

        assertThat(urlConnection.getInputStream())
            .asString(StandardCharsets.UTF_8)
            .contains("<h1>My Library</h1>");
    }

    @Test
    void shouldListBooks() throws IOException {
        var urlConnection = getUrlConnection("/api/books");
        var responseCode = urlConnection.getResponseCode();

        assertThat(responseCode)
                .as(urlConnection.getResponseMessage() + " for " + urlConnection.getURL() )
                .isEqualTo(200);

        assertThat(urlConnection.getContentType())
            .isEqualTo("application/json");
    }

    private HttpURLConnection getUrlConnection(String spec) throws IOException {
        return (HttpURLConnection) new URL(server.getUrl(), spec).openConnection();
    }
}
