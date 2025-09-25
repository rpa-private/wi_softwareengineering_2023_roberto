package ch.fhnw.richards.Week_05.controller;

import ch.fhnw.richards.Week_05.model.Model;
import ch.fhnw.richards.Week_05.view.View;

public class Controller {
    public Controller(Model model, View view) {

        // Create sub-controllers
        new TopBarController(view.getTopBar(), view.getInvoiceView());

        // Top-level controller actions go here, for example, window events

    }
}
