package no.kristiania.edu;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {
    private final Server server;
    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    public LibraryServer(int port) throws IOException {
        this.server = new Server(port);
        server.setHandler(configureWebContext());
        logger.info("Library server configured on port {}", port);
    }

    private WebAppContext configureWebContext() throws IOException {
        var webContext = new WebAppContext();
        webContext.setContextPath("/");

        var resources = Resource.newClassPathResource("/webapp");

        // If the app is in development this
        // folder src/main/resources will exist
        // This allows us to serve the app from resources folder
        // And not target/classes.
        // If the app is published the target/classes directory
        // will be used to serve the React app.
        var sourceDirectory = new File(resources.getFile()
                .getAbsoluteFile()
                .toString()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resources"));

        if(sourceDirectory.isDirectory()) { // This path only available in dev
            webContext.setBaseResource(Resource.newResource(sourceDirectory));
            webContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            webContext.setBaseResource(resources);
        }

        ServletHolder jerseyServlet = webContext.addServlet(ServletContainer.class, "/api/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "no.kristiania.edu");

        return webContext;
    }

    public URL getUrl() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
        logger.info("Server starting {}", getUrl());
    }
}
