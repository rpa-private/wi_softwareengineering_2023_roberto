package ch.fhnw.richards.Week_02.Lambdas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Examples {
	public static ArrayList<String> names = new ArrayList<>();
	static {
		names.add("John"); names.add("Tom"); names.add("Jane"); names.add("Arnold");
		names.add("Ann"); names.add("Sue"); names.add("Aurelia"); names.add("Alfred");
	}

	/**
	 * The list is randomly shuffled before each exercise. Also prints a header
	 * line before each method call
	 */
	public static void main(String[] args) {
		Collections.shuffle(names);
		System.out.println("\n\nExercise 1\n");
		example1();
		
		Collections.shuffle(names);
		System.out.println("\n\nExercise 2\n");
		example2();
		
		Collections.shuffle(names);
		System.out.println("\n\nExercise 3\n");
		example3();
		
		Collections.shuffle(names);
		System.out.println("\n\nExercise 4\n");
		example4();
		
		Collections.shuffle(names);
		System.out.println("\n\nExercise 5\n");
		example5();
	}

	/**
	 * Example 1: Comparator includes a method "naturalOrder" to sort objects
	 * using Comparable
	 * 
	 * - Sort the names in their natural order
	 * 
	 * - Use a lambda expression to print the names to the console.
	 * 
	 * - Print the strings again, this time using a method-reference
	 */
	private static void example1() {
		// Sort into natural order
		names.sort(Comparator.naturalOrder());

		// Print to the console, using a lambda
		names.forEach(s -> System.out.println(s));

		// Print to the console, using a method reference
		names.forEach(System.out::println);
	}

	/**
	 * Example 2: Use the Comparator.comparing method
	 * 
	 * - Use a lambda to sort the names by only their first letter; print them
	 * to the console
	 */
	private static void example2() {
		names.sort(Comparator.comparing(s -> s.charAt(0)));
		names.forEach(System.out::println);
	}

	/**
	 * Example 3: String provides the method "compareToIgnoreCase". Use this
	 * method to sort the strings. Note that you cannot use Comparator in this
	 * case, so your lambda expression appears directly inside sort(...)
	 * 
	 * - Sort the strings ignoring case, using a lambda; print the result
	 * 
	 * - Use a method reference for sorting; print the result
	 */
	private static void example3() {
		// With a lambda
		names.sort( (s1, s2) -> s1.compareToIgnoreCase(s2));
		names.forEach(System.out::println);
		
		// With a method reference
		names.sort( String::compareToIgnoreCase);
		names.forEach(System.out::println);		
	}
	
	/**
	 * Example 4: Use Collection.removeIf
	 * 
	 *  - Remove all names that begin with 'A'.
	 *  
	 *  - Print the remaining names and their lengths.
	 */
	private static void example4() {
		names.removeIf(s -> (s.charAt(0) == 'A'));
		names.forEach(System.out::println);		
	}
	
	/**
	 * Example 5: Note that some names were deleted by exercise 4
	 * 
	 *  - Sort the remaining names by their length; use a method reference
	 *
	 *  - Print each name, followed by its length (e.g., "Ralph 5")
	 */
	private static void example5() {
		names.sort(Comparator.comparing( String::length));
		names.forEach( s -> System.out.println(s + " " + s.length()));
	}
}
