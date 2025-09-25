package ch.fhnw.richards.Week_07.httpWebServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import javafx.concurrent.Task;

/**
 * Model is a thread, so that it can work without blocking the GUI
 */
public class Model extends Thread {
    private final Integer port;
    private final String webRoot;
    private final Logger logger = Logger.getLogger("");

    public Model(int port, String webRoot) {
        super("ServerSocket");
        this.port = port;
        this.webRoot = webRoot;
    }

    @Override
    public void run() {
        try (ServerSocket listener = new ServerSocket(port, 10, null)) {
            logger.info("Listening on port " + port);

            while (true) {
                // Wait for request, then create new thread to service the client
                Socket socket = listener.accept();
                Model_ClientThread client = new Model_ClientThread(socket, webRoot);
                client.start();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
