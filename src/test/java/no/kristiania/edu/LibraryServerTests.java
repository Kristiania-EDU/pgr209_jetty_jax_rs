package no.kristiania.edu;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryServerTests {

    private LibraryServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new LibraryServer(8080);
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


        System.out.println(urlConnection.getContent());
    }

    @Test
    void shouldAddBook() throws IOException {
        var urlConnection = getUrlConnection("/api/books");
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.getOutputStream().write(
            Json.createObjectBuilder()
                .add("title", "BookTitle1")
                .add("author", "bookTitle2")
                .build()
                .toString()
                .getBytes(StandardCharsets.UTF_8)
        );

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
