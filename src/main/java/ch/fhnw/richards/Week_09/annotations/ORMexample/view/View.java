package ch.fhnw.richards.Week_09.annotations.ORMexample.view;

import ch.fhnw.richards.Week_09.annotations.ORMexample.model.Model;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {
    private final Stage stage;
    private final TopBar topBar;
    private final InvoiceView invoiceView;

    public View(Model model, Stage stage) {
        this.stage = stage;

        // Create components
        invoiceView = new InvoiceView(model);
        topBar = new TopBar(model, invoiceView);

        BorderPane root = new BorderPane();
        root.setTop(topBar);

        root.setCenter(invoiceView);
        Scene scene = new Scene(root);
        stage.setTitle("Database example");
        stage.setScene(scene);
    }

    public void start() {
        stage.show();
    }

    public TopBar getTopBar() { return topBar; }
    public InvoiceView getInvoiceView() { return invoiceView; }
}
