package ch.fhnw.richards.Week_07.httpWebServer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class Model_ClientThread extends Thread {
    private static int clientNumber = 0;
    private final Logger logger = Logger.getLogger("");
    private final Socket socket;
    private final String webRoot;

    public Model_ClientThread(Socket socket, String webRoot) {
        super("Client thread" + clientNumber++);
        this.socket = socket;
        this.webRoot = webRoot;
    }

    @Override
    public void run() {
        // Create input and output streams to talk to the client
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream outBinary = socket.getOutputStream();
                PrintWriter outText = new PrintWriter(outBinary);
                ) {

            logger.info("Request from client "
                    + socket.getInetAddress().toString() + " for server "
                    + socket.getLocalAddress().toString());

            // Read request from client. An empty string (length 0) is the end of an HTTP request
            StringBuilder received = new StringBuilder();
            String inString;
            while ((inString = in.readLine()) != null && !inString.isEmpty()) {
                received.append(inString).append("\n");
            }

            // Parse the file name, and place it within our web content directory
            String fileName = parseGetRequest(received.toString());
            logger.info("Requested file: " + fileName + "\n");
            
            // Determine whether or not the file exists, and is readable
            File file = null;
            if (fileName != null) file = new File(fileName);
            if (file == null || !file.exists() || file.isDirectory() || !file.canRead()) {
                // No good, status 404
                logger.info("Status 404\n");
                outText.print("HTTP/1.0 404 \r\n"); // Version and status
                outText.print("Content-Type: text/plain\r\n");
                outText.print("\r\n");
                outText.print("File not found\n");
            } else {
                // All ok, status 200
                logger.info("Status 200\n");
                outText.print("HTTP/1.0 200 \r\n"); // Version and status
                String mediaType = getMediaType(fileName);
                outText.print("Content-Type: " + mediaType + "\r\n");
                outText.print("\r\n");
                outText.flush();
                
                if (mediaType.startsWith("text")) {
                    sendTextFile(outText, file);
                } else {
                    sendBinaryFile(outBinary, file);
                }
                logger.info("File sent");                        
            }
            
            outText.flush(); // Be safe, always "flush"
            socket.close();
        } catch (IOException e) {
            logger.warning(e.toString());
        }
    }

    /**
     * We just use a simple 2-dimensional array to map file extension to media types.
     * You could also use an enumeration, or a HashMap, or...
     * 
     * If we cannot determine the type, we default to the application/octet-stream,
     * which is used for arbitrary binary data.
     * 
     * @param fileName The file name of the file to be sent
     * @return The media type for the HTTP header
     */
    private String getMediaType(String fileName) {
        final String[][] mediaTypes = {
                {"html", "text/html"},
                {"htm", "text/html"},
                {"css", "text/css"},
                {"xml", "text/xml"},
                {"txt", "text/plain"},
                {"jpg", "image/jpeg"},
                {"jpeg", "image/jpeg"},
                {"gif", "image/gif"},
                {"png", "image/png"},
                {"ico", "image/x-icon"},
                {"svg", "image/svg+xml"}
        };
        
        String mediaType = "application/octet-stream";
        int extensionStart = fileName.indexOf('.') + 1;
        if (extensionStart == 0) extensionStart = fileName.length();
        String fileExtension = fileName.substring(extensionStart);
        for (String[] mediaEntry: mediaTypes) {
            if (mediaEntry[0].equals(fileExtension)) {
                mediaType = mediaEntry[1];
                break;
            }
        }
        logger.info("File extension '" + fileExtension + "' maps to media type " + mediaType);
        return mediaType;
    }
    
    /**
     * Send a text file line-by-line
     * @param outClient The print writer for the client
     * @param file The file to send
     */
    private void sendTextFile(PrintWriter outClient, File file) {
        BufferedReader inFile = null;
        try {
            inFile = new BufferedReader(new FileReader(file));
            String line = inFile.readLine();
            while (line != null) {
                outClient.println(line);
                line = inFile.readLine();
            }            
        } catch (Exception e) {
            logger.severe(e.toString());
        } finally {
            if (inFile != null) try { inFile.close(); } catch (Exception ignored) {}
        }
    }

    /**
     * Send a binary file
     * @param outClient The OutputStream for the client
     * @param file The file to send
     */
    private void sendBinaryFile(OutputStream outClient, File file) {
        BufferedInputStream inFile = null;
        try {
            inFile = new BufferedInputStream(new FileInputStream(file));
            int b = inFile.read();
            while (b > -1) {
                outClient.write(b);
                b = inFile.read();
            }            
        } catch (Exception e) {
            logger.severe(e.toString());
        } finally {
            if (inFile != null) try { inFile.close(); } catch (Exception ignored) {}
        }
    }
    
    /**
     * Examine the incoming request. If it is a GET request, locate and return the requested file
     * name. If the request is not a GET request, or if we have any problems, return null.
     * 
     * If the root of our web content is a subdirectory "www" underneath the
     * working directory, then a request for "woof.html" will become "www/woof.html", and a request
     * for "/path/to/woof.html" will become "www/path/to/woof.html".
     * 
     * @param request Incoming request read from the client
     * @return Name of the requested file, or else null
     */
    private String parseGetRequest(String request) {
        String fileName = null;
        
        if (request.regionMatches(0, "GET ", 0, 4)) {
            int fileNameEnd = request.indexOf(" ", 4);
            fileName = request.substring(4, fileNameEnd).trim();
            
            // Sanitize filename
            fileName = fileName.replaceAll("[^0-9_/a-zA-Z\\-\\.]", "");
            if (fileName.length() > 255) fileName = fileName.substring(0, 255);
        }
        
        if (fileName != null) {
            if (fileName.isEmpty() || fileName.equals("/")) { // Default document
                fileName = webRoot + "/index.html";
            } else if (fileName.charAt(0) == '/') {
                fileName = webRoot + fileName;
            } else {
                fileName = webRoot + "/" + fileName;
            }
        }
        return fileName;
    }	
}
