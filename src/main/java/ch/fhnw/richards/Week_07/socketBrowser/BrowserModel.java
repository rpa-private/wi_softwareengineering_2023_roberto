package ch.fhnw.richards.Week_07.socketBrowser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class BrowserModel {
    public String browse(String ipAddress, Integer port) {
        String lineIn;
        StringBuffer pageContent = new StringBuffer();

        // Network errors are always possible
        try (Socket s = new Socket(ipAddress, port);
                OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream());
                BufferedReader inReader = new BufferedReader(new InputStreamReader(s.getInputStream()));) {

            // Send our request, using the HTTP 1.0 protocol
            // Note: HTTP specifies \r\n line endings, though most programs don't care
            out.write("GET / HTTP/1.0\r\n");
            out.write("User-Agent: Browser0\r\n");
            out.write("Host: " + ipAddress + ":" + port + "\r\n");
            out.write("Accept: text/html, */*\r\n\r\n"); // Blank line at the end!!!
            out.flush();

            // Start reading
            while ((lineIn = inReader.readLine()) != null) {
                pageContent.append(lineIn + "\n");
            }
        } catch (Exception err) {
            // If an error occurred, show the error message in the content
            pageContent.append("ERROR: ").append(err.toString());
        }

        return pageContent.toString();
    }
}
