package ch.fhnw.richards.Week_06.solarSystem;

import javafx.scene.paint.Color;

/**
 * We simulate planetary orbits simply, just moving them around a circle at the
 * correct speed. This is not a true gravitational simulation.
 */
public class Planet extends Thread {
	public static final double MAX_POS = 1400000000000.0; // Orbit of Saturn
	public static final double MAX_RADIUS = 100000000.0; // A bit larger than Jupiter

	// Base attributes
	private String name;
	private double orbitalRadius; // meters
	private double orbitalVelocity; // meters/second
	private double radius; // meters
	private Color color; // Approximate color of planet

	// Current position in orbit
	private double angle;
	private double x;
	private double y;

	/**
	 * When a new planet is created, we take the orbital radius and randomly
	 * generate a position somewhere on that orbit.
	 */
	public Planet(String name, double orbitalRadius, double orbitalVelocity, double radius, Color color) {
		super(name);
		this.name = name;
		this.orbitalRadius = orbitalRadius;
		this.orbitalVelocity = orbitalVelocity;
		this.radius = radius;
		this.color = color;

		// Initialize to random orbital position
		angle = Math.random() * 2 * Math.PI;

		this.setDaemon(true); // Will be killed when program ends
		this.start(); // Begin simulation
	}

	/**
	 * Our simulation runs at one earth-day per tick. This means that we need to
	 * calculate the movement of this planet (in radians) in one earth day, and then
	 * calculate its position based on that angle
	 */
	@Override
	public void run() {
		while (true) {
			final double earthPeriod = 365.24 * 86400;
			final double earthRadiansPerDay = 2 * Math.PI / 365.24;

			double circumference = 2 * orbitalRadius * Math.PI;
			double period = circumference / orbitalVelocity;
			double radiansPerDay = earthRadiansPerDay * earthPeriod / period;

			angle += radiansPerDay;
			x = Math.sin(angle) * orbitalRadius;
			y = Math.cos(angle) * orbitalRadius;
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

}
