package ch.fhnw.richards.Week_11.translator;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class View {
    Stage stage;
    Model model;
    Preferences preferences;

    // Initial translator to use; default to US English
    Translator translator;

    // UI components
    MenuBar menus = new MenuBar();
    Menu menuLanguage = new Menu();
    MenuItem menuLanguageEN = new MenuItem();
    MenuItem menuLanguageDE = new MenuItem();

    Label lblText = new Label();

    public View(Stage stage, Model model, Preferences preferences) {
        this.stage = stage;
        this.model = model;
        this.preferences = preferences;
        this.translator = new Translator(preferences.get("locale", "en-US"));

        menus.getMenus().add(menuLanguage);
        menuLanguage.getItems().addAll(menuLanguageEN, menuLanguageDE);

        BorderPane root = new BorderPane();
        root.setTop(menus);
        root.setCenter(lblText);

        updateTexts();

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
    }

    public void start() {
        stage.show();
    }

    /**
     * Update all texts shown in the view
     */
    protected void updateTexts() {
        // The menu entries
        menuLanguage.setText(translator.getString("program.menu.language"));
        menuLanguageEN.setText(translator.getString("program.menu.language.english"));
        menuLanguageDE.setText(translator.getString("program.menu.language.german"));

        // Other controls
        lblText.setText(translator.getString("label.text"));

        // Window title
        stage.setTitle(translator.getString("program.name"));
    }
}
