package ch.fhnw.richards.Week_06.whac_a_mole;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This is not MVC-based. In this design, each button handles its own events.
 */
public class Whacamole extends Application {
    private int score = -1;
    protected Label lblScore = new Label("Score: 0 moles whacked!");

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Note the dependencies between model, view and controller. Additionally, the view needs the
     * primaryStage created by JavaFX.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Whac-a-mole");

        BorderPane root = new BorderPane();
        root.setTop(lblScore);

        GridPane molePane = new GridPane();
        root.setCenter(molePane);

        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                MoleButton mole = new MoleButton(this);
                molePane.add(mole, i, j);
            }
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Whacamole.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }

    /**
     * We not only end JavaFX, we also end the JavaVM. This is the brutal way to kill all of the
     * button threads. An alternative would be to declare the button threads as daemons.
     */
    @Override
    public void stop() {
        Platform.exit(); // Stop JavaFX
        System.exit(0); // End Java - to kill all button threads
    }

    void whack() {
        score++;
        lblScore.setText("Score: " + score + " moles whacked!");
    }
}
