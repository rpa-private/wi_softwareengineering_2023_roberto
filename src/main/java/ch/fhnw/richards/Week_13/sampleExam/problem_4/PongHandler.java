package ch.fhnw.richards.Week_13.sampleExam.problem_4;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PongHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try (// Get the input and output streams
             BufferedReader in = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
             OutputStreamWriter out = new OutputStreamWriter(httpExchange.getResponseBody());
        ) {
            int statusCode = 418; // Pessimistic: assume an error
            JSONObject jsonResponse = new JSONObject();

            String requestMethod = httpExchange.getRequestMethod();
            if (requestMethod.equals("POST")) {
                JSONObject JSONin = readJSON(in); // Assume this method exists
                if (JSONin != null) { // If not null, assume all values are present
                    String value = JSONin.getString("value");
                    String answer = value.toUpperCase();
                    jsonResponse.put("answer", answer);
                    statusCode = 200;
                } else {
                    jsonResponse.put("Error", "Invalid JSON in request");
                }
            } else { // Unsupported request type
                jsonResponse.put("Error", "Invalid HTTP request method");
            }

            // Create a response
            String response = jsonResponse.toString();
            httpExchange.sendResponseHeaders(statusCode, response.length());
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
