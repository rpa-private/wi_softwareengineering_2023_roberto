package ch.fhnw.richards.Week_07.httpWebServer;

/**
 * This is a simple HTTP web-server. The port number must be specified
 * on the command line.
 *
 * By default, the program will expect web-root in the www subdirectory
 * of whatever directory is is started in. Alternatively, both port number
 * and web-root (absolute path) can be provided as command-line parameters.
 */
public class WebServer {

    public static void main(String[] args) {
        int portNumber = 0;
        String webRoot = "www";

        // If not provided on the command line, read port number
        if (args.length > 0) {
            portNumber = validatedPortNumber(args[0]);

            if (args.length > 1) {
                webRoot = args[1];
            }
        }
        if (portNumber > 0) {
            Model model = new Model(portNumber, webRoot);
            model.setDaemon(false); // Must remain running when main-thread exits
            model.start();
        } else {
            System.out.println("No port number provided!");
        }
    }
    private static int validatedPortNumber(String portText) {
        int result = 0;
        try {
            int portNumber = Integer.parseInt(portText);
            if (portNumber >= 1 & portNumber <= 65535) {
                result = portNumber;
            }
        } catch (NumberFormatException ignored) {
        }
        return result;
    }
}
