package ch.fhnw.richards.Week_07.socketBrowser;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This example represents a very simple browser. We correctly fetch web-pages using
 * the HTTP/1.0 protocol. However, these pages are simply displayed in raw HTML
 */
public class Browser extends Application {
    private BrowserView view;
    private BrowserController controller;
    private BrowserModel model;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Create the standard MVC pattern
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the GUI
        model = new BrowserModel();
        view = new BrowserView(primaryStage, model);
        controller = new BrowserController(model, view);

        // Display the GUI after all initialization is complete
        view.start();
    }
}
