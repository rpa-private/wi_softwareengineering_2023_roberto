package ch.fhnw.richards.Week_11.translator;

import java.util.Locale;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.menuLanguageEN.setOnAction( event -> {
            updateLanguage("en-US");
        } );
        view.menuLanguageDE.setOnAction( event -> {
            updateLanguage("de-CH");
        } );
    }

    private void updateLanguage(String localeString) {
        Locale locale = Locale.forLanguageTag(localeString);
        Locale.setDefault(locale);
        view.preferences.put("locale", localeString);
        view.translator = new Translator(localeString);
        view.updateTexts();
    }
}
