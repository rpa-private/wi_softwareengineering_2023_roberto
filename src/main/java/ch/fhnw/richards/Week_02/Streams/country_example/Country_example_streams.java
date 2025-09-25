package ch.fhnw.richards.Week_02.Streams.country_example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Country_example_streams {
	public static void main(String[] args) {
		List<String> names = getNames_SmallCountries(Country.countries);
		
		// Print out the country names
		for (String name : names) System.out.print(name + " ");
	}

	private static List<String> getNames_SmallCountries(ArrayList<Country> countries) {
		return countries.stream()
				.filter( c -> c.getArea() < 100000 )
				.sorted( (c1, c2) -> c1.getName().compareTo(c2.getName()))
				.map( c -> c.getName() )
				.collect(Collectors.toList());
	}
}
