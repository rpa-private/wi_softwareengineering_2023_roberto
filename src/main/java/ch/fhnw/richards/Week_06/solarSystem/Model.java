package ch.fhnw.richards.Week_06.solarSystem;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Model {
	private final ArrayList<Planet> planets = new ArrayList<>();

	public Model() {
		Planet mercury = new Planet("Mercury",  57200000000.0, 47362.0,  2440000.0, Color.LIGHTSLATEGRAY);
		Planet venus = new Planet("Venus",     108939000000.0, 35020.0,  6051800.0, Color.PALEGOLDENROD);
		Planet earth = new Planet("Earth",     149598000000.0, 29780.0,  6371000.0, Color.LIGHTBLUE);
		Planet mars = new Planet("Mars",       227500000000.0, 24007.0,  3390000.0, Color.ORANGERED);
		Planet jupiter = new Planet("Jupiter", 800000000000.0, 13070.0, 69911000.0, Color.PALEVIOLETRED);
		Planet saturn = new Planet("Saturn",  1380000000000.0,  9680.0, 58232000.0, Color.GOLD);
		
		planets.add(mercury);
		planets.add(venus);
		planets.add(earth);
		planets.add(mars);
		planets.add(jupiter);
		planets.add(saturn);
	}
	
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
}
