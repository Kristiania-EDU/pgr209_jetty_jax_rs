package no.kristiania.edu;

public class Program {
    public static void main(String[] args) throws Exception {
        LibraryServer server = new LibraryServer(8080);
        server.start();
        System.out.println(server.getUrl());
    }
}
