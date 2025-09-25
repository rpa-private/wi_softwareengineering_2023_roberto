package ch.fhnw.richards.Week_06.solarSystem;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class View {
	public final int MAX_POS = 250;
	public final int MAX_SIZE = 10;
	Stage stage;
	Model model;
	Group shapes = new Group();

	public View(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
		
		shapes.getChildren().add(new Circle(MAX_POS, MAX_POS, 5, Color.YELLOW));

		Scene scene = new Scene(shapes, MAX_POS * 2, MAX_POS * 2, Color.BLACK);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Solar system simulation");
	}

	public void start() {
		update();
		stage.show();
	}

	public void update() {
		ObservableList<Node> nodes = shapes.getChildren();

		Platform.runLater(() -> { // JavaFX update - remove all planets
			while (nodes.size() > 1) nodes.remove(1);
		});

		for (Planet p : model.getPlanets()) {
			int x = translatePosition(p.getX());
			int y = translatePosition(p.getY());
			int s = translateSize(p.getRadius());

			// Only display planets that are actually on the screen
			if (x > 0 && x < 2 * MAX_POS && y > 0 && y < 2 * MAX_POS) {
				Circle circle = new Circle(x, y, s, p.getColor());

				Platform.runLater(() -> nodes.add(circle)); // JavaFX update
			}
		}
	}

	/**
	 * Map simulation positions to pixels
	 */
	private int translatePosition(double position) {
		return (int) (position * MAX_POS / Planet.MAX_POS + MAX_POS);
	}

	/**
	 * Map simulation sizes to pixels, with a minimum of 1
	 */
	private int translateSize(double size) {
		int s = (int) (size * MAX_SIZE / Planet.MAX_RADIUS);
		return (s < 1) ? 1 : s;
	}
}
