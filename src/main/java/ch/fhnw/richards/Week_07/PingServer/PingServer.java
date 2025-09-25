package ch.fhnw.richards.Week_07.PingServer;

import com.sun.net.httpserver.*;

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
    private static int port = 50001;

    public static void main(String[] args) {
        try {
            // Read command-line parameter, if it exists
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
                if (port < 1 || port > 65535) throw new Exception("Invalid port number");
            }

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            // Create a context for a specific path and associate a handler with it
            // Important: See the documentation for HttpServer for how contexts are matched.
            // For example, the following context will match /pingg and /ping/more, unless
            // some other, more specific context matches those strings.
            server.createContext("/ping", new PingHandler());

            // If desired, use multiple threads for processing (here, with 4 threads)
            server.setExecutor(Executors.newFixedThreadPool(4));

            // Start the server
            server.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
