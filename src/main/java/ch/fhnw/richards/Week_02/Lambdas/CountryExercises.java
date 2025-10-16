package ch.fhnw.richards.Week_02.Lambdas;

import java.util.*;

public class CountryExercises {
	static List<Country> countries = new ArrayList<>(Arrays.asList(new Country("Schweiz", 41285),
			new Country("Germany", 357021), new Country("France", 675417), new Country("Italy", 301230),
			new Country("Austria", 83858), new Country("Russia", 17098246), new Country("Luxembourg", 2586),
			new Country("Liechtenstein", 160), new Country("United Kingdom", 244820)));

	public static void main(String[] args) {
		System.out.println("\n\nExercise 1\n");
		example1();

		Collections.shuffle(countries);
		System.out.println("\n\nExercise 2\n");
		example2();

		Collections.shuffle(countries);
		System.out.println("\n\nExercise 3\n");
		example3();

		Collections.shuffle(countries);
		System.out.println("\n\nExercise 4\n");
		example4();

		Collections.shuffle(countries);
		System.out.println("\n\nExercise 5\n");
		example5();
	}

	/**
	 * Sort countries by name, using List.sort, Comparator.comparing and a
	 * lambda. Then print them using a lambda
	 */
	public static void example1() {
		countries.sort(Comparator.comparing(country -> country.getName()));
		countries.forEach(c -> System.out.println(c));
	}

	/**
	 * Repeat exercise 1, but replace both lambdas with method references
	 */
	public static void example2() {
		countries.sort(Comparator.comparing(Country::getName));
		countries.forEach(System.out::println);
	}

	/**
	 * Print all countries that have an area < 100000. Hint: remember that you
	 * can put multiple statements in a lambda.
	 */
	public static void example3() {
		countries.stream()
		.filter(c -> c.getArea() < 10000)
		.forEach(System.out::println);

		//countries.removeIf(c -> c.getArea() < 10000);
	}

	/**
	 * Implement Comparable<Country> for the class Country. Sort the countries
	 * using their natural order, and print using a method reference.
	 */
	public static void example4() {
		countries.sort(Comparator.naturalOrder());
		countries.forEach(System.out::println);
	}

	/**
	 * Replace all countries in the list with new countries, where the name includes the two-letter abbreviation.
	 * Take the first two letters of the country name as the abbreviation. For example, "Germany" becomes "GE: Germany".
	 * Sort the new countries by the first two letters (by the abbreviations), then print the countries
	 */
	public static void example5() {
		countries.replaceAll(c -> {
			String name = c.getName();
			String abbr = name.substring(0, Math.min(2, name.length())).toUpperCase(java.util.Locale.ROOT);
			return new Country(abbr + ": " + name, c.getArea());
		});
		countries.sort(java.util.Comparator.comparing(Country::getName));
		countries.forEach(c -> System.out.println(c.getName()));
	}
}
