package ch.fhnw.richards.Week_09.annotations.ORMexample.controller;

import ch.fhnw.richards.Week_09.annotations.ORMexample.model.Model;
import ch.fhnw.richards.Week_09.annotations.ORMexample.view.View;

public class Controller {
    public Controller(Model model, View view) {

        // Create sub-controllers
        new TopBarController(view.getTopBar(), view.getInvoiceView());

        // Top-level controller actions go here, for example, window events

    }
}
