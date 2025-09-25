package ch.fhnw.richards.Week_02.Lambdas.countrySort_v5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountryPopulationSort {
	static List<Country> countries = new ArrayList<>(Arrays.asList(
			new Country("Switzerland", 41285, 8850000),
			new Country("Germany", 357021, 84550000),
			new Country("France", 675417, 65700000),
			new Country("Austria", 83858, 9200000)));

	public static void main(String[] args) {
		countries.sort( (c1, c2) -> Integer.compare(c1.getPopulation(), c2.getPopulation()));
		
		for (Country c : countries) System.out.println(c);
	}

}
