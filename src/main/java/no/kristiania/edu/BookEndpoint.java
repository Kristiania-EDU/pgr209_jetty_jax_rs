package no.kristiania.edu;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import javax.json.Json;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BookEndpoint {
    private static List<Book> books = new ArrayList<Book>()
    {{
        new Book("Art of thinking clear", "Rolf Dobelli");
    }};

    @Path("/")
    @GET
    public Response getListOfBooks() {
        var book = new Book("Art of thinking clear", "Rolf Dobelli");
        var books = List.of(book);
        var jsonB = JsonbBuilder.create(
                new JsonbConfig().withFormatting(true));
        var json = jsonB.toJson(books);

        return Response.ok(json).build();
    }

    @Path("/")
    @POST
    public Response addBook(String body) {
        var object = Json.createReader(new StringReader(body)).readObject();
        var book = new Book(
            object.getString("author"),
            object.getString("title")
        );

        books.add(book);

        return Response.ok(book).build();
    }
}
