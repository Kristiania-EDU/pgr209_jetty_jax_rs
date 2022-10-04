package no.kristiania.edu;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {
    private final Server server;
    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    public LibraryServer(int port) {
        this.server = new Server(port);

        var webContext = new WebAppContext();
        webContext.setContextPath("/");
        webContext.setBaseResource(Resource.newClassPathResource("/webapp"));
        server.setHandler(webContext);
        logger.info("Library server configured on port {}", port);
    }

    public URL getUrl() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
        logger.info("Server starting {}", getUrl());
    }
}
