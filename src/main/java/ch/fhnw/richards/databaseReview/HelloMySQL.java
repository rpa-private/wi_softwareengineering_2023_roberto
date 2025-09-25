package ch.fhnw.richards.databaseReview;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelloMySQL extends Application {
    Label lblIP = new Label("IP address");
    TextField txtIP = new TextField("127.0.0.1");
    Label lblPort = new Label("Port");
    TextField txtPort = new TextField("3306");
    Label lblUser = new Label("User");
    TextField txtUser = new TextField("root");
    Label lblPassword = new Label("Password");
    PasswordField txtPassword = new PasswordField();
    Button btnGo = new Button("Go");
    
    TextArea txtLog = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridControls = new GridPane();
        gridControls.setHgap(20);
        gridControls.setVgap(5);
        gridControls.add(lblIP, 0, 0);
        gridControls.add(txtIP, 1, 0);
        gridControls.add(lblPort, 0, 1);
        gridControls.add(txtPort, 1, 1);
        gridControls.add(lblUser, 2, 0);
        gridControls.add(txtUser, 3, 0);
        gridControls.add(lblPassword, 2, 1);
        gridControls.add(txtPassword, 3, 1);
        gridControls.add(btnGo, 4, 0);
        
        
        BorderPane root = new BorderPane();
        root.setTop(gridControls);
        root.setCenter(txtLog);
        
        btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                testDatabase();
            }
        });
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello, MySQL");
        primaryStage.show();
    }
    
    /**
     * Note: to connect to an existing database, add the database name after the "/",
     * i.e., "jdbc:mysql://server:port/database". If you have connected to a specific
     * database, you do not need the "USE" command.
     */
    private void testDatabase() {
        Connection cn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        String serverInfo = "jdbc:mysql://" + txtIP.getText() + ":" + txtPort.getText() + "/";
        String optionInfo = "?connectTimeout=5000";
        String username = txtUser.getText();
        String password = txtPassword.getText();
        txtLog.appendText("Opening connection to " + serverInfo + "\n");
        try {
            cn = DriverManager.getConnection(serverInfo + optionInfo, username, password);
            
            // Execute some SQL
            stmt = cn.createStatement();
            stmt.execute("USE woof");
            txtLog.appendText("Creating table\n");
            stmt.execute("CREATE TABLE hello " +
                            "(ID INT NOT NULL AUTO_INCREMENT, " +
                            "Text VARCHAR(45) NOT NULL, " +
                            "PRIMARY KEY (`ID`))");
            
            // Add some data
            txtLog.appendText("Adding data\n");
            stmt.execute("INSERT INTO hello (ID, Text) VALUES (1, 'Aaa')");
            stmt.execute("INSERT INTO hello (ID, Text) VALUES (2, 'Ccc')");
            stmt.execute("UPDATE hello SET Text='Bbb' WHERE ID=2");
            stmt.close();
            
            // Run a query - note the options on createStatement()
            txtLog.appendText("Running a query\n");
            stmt = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * from hello");
            while (rs.next()) {
                txtLog.appendText("ID = " + rs.getInt(1) + ", Text = " + rs.getString("Text") + "\n");
            }
        } catch (SQLException e) {
            txtLog.appendText("Exception " + e.toString() + "\n");
        } finally {
            // Release resources - important!
            txtLog.appendText("Freeing resources\n");
            if (rs != null) try {
                if (!rs.isClosed()) rs.close();
            } catch (Exception e) {}
            if (stmt != null) try {
                if (!stmt.isClosed()) stmt.close();
            } catch (Exception e) {}
            if (cn != null) try {
                if (!cn.isClosed()) cn.close();
            } catch (Exception e) {}
        }
    }   
}
