package no.kristiania.edu;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
public class BookEndpoint {
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
    public Response addBook() {
        return Response.ok().header("Content-Type", "application/json").build();
    }
}
