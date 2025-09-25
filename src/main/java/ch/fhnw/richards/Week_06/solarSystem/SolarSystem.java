package ch.fhnw.richards.Week_06.solarSystem;

import javafx.application.Application;
import javafx.stage.Stage;

public class SolarSystem extends Application {

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Model model = new Model();
		View view = new View(primaryStage, model);
		Controller controller = new Controller(model, view);
		view.start();
	}

}
