package ch.fhnw.richards.Week_11.httpHeaders;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * This class represents a client, from the perspective of the server. We
 * communicate with the client using a socket.
 */
public class PingHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try (// Get the input and output streams
             BufferedReader in = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
             OutputStreamWriter out = new OutputStreamWriter(httpExchange.getResponseBody());
        ) {
            // Values we will set for our response.
            int statusCode;
            JSONObject jsonResponse = new JSONObject();

            String requestMethod = httpExchange.getRequestMethod();
            if (requestMethod.equals("GET")) {
                statusCode = 200;
                Headers headers = httpExchange.getRequestHeaders();
                List<String> languages = headers.get("Accept-language");
                // Assume there is only one header, or anyway, only show the first
                jsonResponse.put("Locale list", languages.get(0));
            } else { // Unsupported request type
                statusCode = 418; // Can't make coffee in a teapot
                jsonResponse.put("Error", "Invalid HTTP request method");
            }

            // Create a response
            String response = jsonResponse.toString();
            httpExchange.sendResponseHeaders(statusCode, response.length());

            // Write the response to the output stream
            out.write(response);
        }
    }

    private JSONObject readJSON(BufferedReader in) {
        JSONObject jsonIn = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonText = stringBuilder.toString();
            jsonIn = new JSONObject(jsonText);
        } catch (Exception e) {
            // If anything goes wrong, return null
        }
        return jsonIn;
    }
}
