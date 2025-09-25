package ch.fhnw.richards.Week_07.socketBrowser;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class BrowserView {
    private BrowserModel model;
    private Stage stage;

    protected Label lblIP = new Label("IP");
    protected TextField txtIP = new TextField("127.0.0.1");
    protected Label lblPort = new Label("Port");
    protected TextField txtPort = new TextField("8080");
    protected Button btnGo = new Button("Go");
    protected TextArea txtWebPage = new TextArea();
    
    protected BrowserView(Stage stage, BrowserModel model) {
        this.stage = stage;
        this.model = model;
        
        BorderPane root = new BorderPane();

        HBox topBox = new HBox();
        topBox.setId("TopBox");
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        root.setTop(topBox);
        
        topBox.getChildren().addAll(lblIP, txtIP, spacer1, lblPort, txtPort, spacer2, btnGo);
        txtIP.setId("IP");
        txtPort.setId("Port");
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);
        scrollPane.setContent(txtWebPage);
        txtWebPage.setWrapText(true);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("Browser.css").toExternalForm());
        stage.setTitle("Browser");
        stage.setScene(scene);;
    }
    
    public void start() {
        stage.show();
    }
}
