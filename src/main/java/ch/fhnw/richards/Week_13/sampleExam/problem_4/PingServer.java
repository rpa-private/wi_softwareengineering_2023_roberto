package ch.fhnw.richards.Week_13.sampleExam.problem_4;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * This is a headless server that answers RESTful queries. You can send either a GET request or
 * a POST request to /ping. The GET request is answered with a static text. The POST request
 * returns the same information that was sent.
 * <p>
 * By default, the server uses Port 50001. However, this can be changed by including a different
 * port parameter on the command line. Note that using ports < 1024 may be restricted by your
 * operating system.
 */
public class PingServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(50001), 0);

        // Create a context for a specific path and associate a handler with it
        server.createContext("/ping", new PingHandler());
        server.createContext("/pong", new PongHandler());

        // Start the server
        server.start();
    }
}
