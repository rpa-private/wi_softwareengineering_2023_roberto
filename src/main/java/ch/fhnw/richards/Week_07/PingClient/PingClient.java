package ch.fhnw.richards.Week_07.PingClient;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * This is a simple, console-based client that sends a series of ping-requests to a server.
 * It demonstrates the use of HttpRequest
 * <p>
 * By default, the client accesses http://127.0.0.1:50001". However, this can be changed by including a
 * different ip address and port parameter on the command line. If any command line arguments are present,
 * then *both* must be present: IP-address and port-number. Little error-checking is done.
 * <p>
 * You can also test against http://httpbin.org/ although it is slow, and requires a higher timeout value
 * See: https://stackoverflow.com/questions/5725430/http-test-server-accepting-get-post-requests
 * <p>
 * If you want to use an HTTPS server, see the comments in the code
 */
public class PingClient {
    private static String server = "127.0.0.1"; // IP address; can also be a symbolic address like javaproject.ch
    private static int port = 50001; // Use 80 for standard http, or 443 for standard https

    public static void main(String[] args) {
        try {
            // Read command-line parameters, if they exist
            if (args.length > 0) {
                server = args[0];
                port = Integer.parseInt(args[1]);
                if (port < 1 || port > 65535) throw new Exception("Invalid port number");
            }

            URI uri = new URI("http://" + server + ":" + port + "/ping"); // If needed, change http to https
            HttpClient client = HttpClient.newBuilder()
                    // .sslContext(SSLContext.getDefault()) // Uncomment if you want to use https
                    .build();

            // Send a simple GET request to /ping
            sendGetRequest(uri, client);

            // Send a POST request to /ping
            sendPostRequest(uri, client);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Send a GET request to /ping
    private static void sendGetRequest(URI uri, HttpClient client) {
        String result;
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(uri)
                    .timeout(Duration.of(3, ChronoUnit.SECONDS))
                    .GET().build();
            HttpResponse<String> response;
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = "GET Request: Status code = " + response.statusCode();
            String body = response.body();
            JSONObject jsonIn = readJSON(body);
            if (jsonIn == null) result += " (body is invalid JSON)";
            result += ", body: " + body;
        } catch (IOException e) {
            result = "GET Request: IO Exception: " + e;
        } catch (InterruptedException e) {
            result = "GET Request: Timeout";
        } catch (Exception e) {
            result = "GET Request: Unexpected error!";
        }
        System.out.println(result);
    }

    // Send a POST request to /ping
    private static void sendPostRequest(URI uri, HttpClient client) {
        String result;
        try {
            String jsonSent = "{\"MeaningOfLife\":\"42\"}";

            HttpRequest request = HttpRequest.newBuilder().uri(uri)
                    .header("Content-Type", "application/json")
                    .timeout(Duration.of(3, ChronoUnit.SECONDS))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonSent))
                    .build();
            HttpResponse<String> response;
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = "POST Request: Status code = " + response.statusCode();
            String body = response.body();
            JSONObject jsonIn = readJSON(body);
            if (jsonIn == null) result += " (body is invalid JSON)";
            result += ", body: " + body;
        } catch (IOException e) {
            result = "POST Request: IO Exception" + e;
        } catch (InterruptedException e) {
            result = "POST Request: Timeout" + e;
        } catch (Exception e) {
            result = "POST Request: Unexpected error!" + e;
        }
        System.out.println(result);
    }

    private static JSONObject readJSON(String in) {
        JSONObject jsonIn = null;
        try {
            jsonIn = new JSONObject(in);
        } catch (Exception e) {
            // If anything goes wrong, return null
        }
        return jsonIn;
    }
}
