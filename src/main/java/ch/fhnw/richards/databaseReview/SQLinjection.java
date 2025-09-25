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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class SQLinjection extends Application {
    // connection is required by entire program
    Connection cn = null;

    // top controls for DB connection
    Label lblIP = new Label("IP address");
    TextField txtIP = new TextField("127.0.0.1");
    Label lblPort = new Label("Port");
    TextField txtPort = new TextField("3306");
    Label lblUser = new Label("User");
    TextField txtUser = new TextField("root");
    Label lblPassword = new Label("Password");
    PasswordField txtPassword = new PasswordField();
    Button btnGo = new Button("Connect");
    
    TextArea txtLog = new TextArea();

    // bottom controls for login
    Label lblStudent = new Label("Student");
    TextField txtStudent = new TextField();
    Label lblStudentPassword = new Label("Password");
    PasswordField txtStudentPassword = new PasswordField();
    Region spacer = new Region();
    Button btnLogin = new Button("Login");
    
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
        
        HBox loginControls = new HBox(5);
        loginControls.getChildren().addAll(lblStudent, txtStudent, lblStudentPassword, txtStudentPassword, spacer, btnLogin);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        BorderPane root = new BorderPane();
        root.setTop(gridControls);
        root.setCenter(txtLog);
        root.setBottom(loginControls);
        
        btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createDatabase();
            }
        });
        
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                login();
            }
        });
        
        btnLogin.setDisable(true);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SQL Injection Demo");
        primaryStage.show();
    }
    
    /**
     * Release the database connection only when the program ends
     */
    @Override
    public void stop() {
        if (cn != null) try {
            if (!cn.isClosed()) cn.close();
        } catch (Exception e) {}
    }
    
    /**
     * Always create a fresh database, because we intend to destroy it
     */
    private void createDatabase() {
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
            txtLog.appendText("Deleting students table if it exists\n");
            stmt.execute("DROP TABLE IF EXISTS students");
            txtLog.appendText("Creating students table\n");
            stmt.execute("CREATE TABLE students " +
                            "(ID INT NOT NULL AUTO_INCREMENT, " +
                            "Name VARCHAR(45) NOT NULL, " +
                            "Password VARCHAR(45) NOT NULL, " +
                            "PRIMARY KEY (`ID`))");
            
            // Add some data
            txtLog.appendText("Adding data\n");
            stmt.execute("INSERT INTO students (ID, Name, Password) VALUES (1, 'Robert', 'Secret')");
            stmt.execute("INSERT INTO students (ID, Name, Password) VALUES (2, 'Amelia', 'Secret')");
            stmt.close();
            
            // Enable login control
            btnLogin.setDisable(false);
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
        }
    }
    
    private void login() {
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = cn.createStatement();
            String query = "SELECT COUNT(*) FROM students WHERE name = '" + txtStudent.getText() + "' AND password = '" + txtStudentPassword.getText() + "'";
            txtLog.appendText("\nQuery: " + query + "\n");
            rs = stmt.executeQuery(query);
            rs.next();

            boolean loginSuccessful = (rs.getInt(1) > 0);            
            txtLog.appendText("Login successful: " + loginSuccessful + "\n\n");
            
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
        }
    }
}
