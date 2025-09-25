package ch.fhnw.richards.Week_02.Streams.country_example;

import java.util.ArrayList;
import java.util.Arrays;

public class Country {
	// Test data
	public static final ArrayList<Country> countries = new ArrayList<>(Arrays.asList(new Country("Switzerland", 41285),
			new Country("Germany", 357021), new Country("France", 675417), new Country("Italy", 301230),
			new Country("Austria", 83858), new Country("Russia", 17098246), new Country("Luxembourg", 2586),
			new Country("Liechtenstein", 160), new Country("United Kingdom", 244820)));
	
	// The Country class
	private final String name;
	private final double area;

	public Country(String name, double area) {
		this.name = name;
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public double getArea() {
		return area;
	}

	@Override
	public String toString() {
		return String.format("%s (%.2f km2)", name, area);
	}
}