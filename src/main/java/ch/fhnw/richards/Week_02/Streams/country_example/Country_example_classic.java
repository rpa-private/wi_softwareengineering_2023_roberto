package ch.fhnw.richards.Week_02.Streams.country_example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Country_example_classic {

	public static void main(String[] args) {
		// Get names of small countries
		ArrayList<String> names = getNames_SmallCountries(Country.countries);
		
		// Print out the names
		for (String name : names) System.out.print(name + " ");
	}

	private static ArrayList<String> getNames_SmallCountries(ArrayList<Country> countries) {
		// Create a new list containing only small countries
		ArrayList<Country> smallCountries = new ArrayList<>();
		for (Country c : countries) {
			if (c.getArea() < 100000) smallCountries.add(c);
		}
		
		// Sort the new list by name
		Collections.sort(smallCountries, new Comparator<Country>() {
			@Override
			public int compare(Country c1, Country c2) {
				return c1.getName().compareTo(c2.getName());
			}
		});
		
		// Create a new list of the country names
		ArrayList<String> names = new ArrayList<>();
		for (Country c : smallCountries) {
			names.add(c.getName());
		}
		
		return names;
	}
}
