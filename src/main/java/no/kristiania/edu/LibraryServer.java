package no.kristiania.edu;

import org.eclipse.jetty.server.Server;

import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {
    private final Server server;

    public LibraryServer(int port) {
        this.server = new Server(port);
    }

    public URL getUrl() throws MalformedURLException {
        return server.getURI().toURL();
    }
}
