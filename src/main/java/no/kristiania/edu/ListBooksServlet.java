package no.kristiania.edu;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListBooksServlet extends HttpServlet {
    private List<Book> books = new ArrayList<Book>()
    {{
        new Book("Art of thinking clear", "Rolf Dobelli");
    }};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var jsonB = JsonbBuilder.create(
            new JsonbConfig().withFormatting(true));
        var json = jsonB.toJson(books);

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
