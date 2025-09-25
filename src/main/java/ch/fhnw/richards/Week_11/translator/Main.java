package ch.fhnw.richards.Week_11.translator;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Main extends Application {
    private Model model;
    private View view;
    private Controller controller;
    private Preferences preferences;

    public static void main(String[] args) {
        launch();
    }

    // Good time to load user preferences
    @Override
    public void init() {
        Preferences root = Preferences.userRoot();
        preferences = root.node("/ch/fhnw/richards/Week_11/translator");
    }

    @Override
    public void start(Stage stage) throws Exception {
        model = new Model();
        view = new View(stage, model, preferences);
        controller = new Controller(model, view);
        view.start();
    }

    @Override
    public void stop() {
        try {
            preferences.flush(); // Ensure changes are saved
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
    }
}
