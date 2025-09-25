package ch.fhnw.richards.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloWorld extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Label lblHello = new Label("Hello, World");
        BorderPane root = new BorderPane();
        root.setCenter(lblHello);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
