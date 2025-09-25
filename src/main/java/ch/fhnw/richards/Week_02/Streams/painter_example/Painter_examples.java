package ch.fhnw.richards.Week_02.Streams.painter_example;

import java.util.Comparator;

public class Painter_examples {

	public static void main(String[] args) {
		// Print information on all painters
		PainterData.painters.stream().forEach(System.out::println);

		// Print information on all living painters
		PainterData.painters.stream().filter(c -> c.getDeathYear() == null).forEach(System.out::println);

		// Print all Spanish painters, sorted by name
		PainterData.painters.stream()
		.filter(c -> c.getNationality().equals(CountryCode.ES))
		.sorted( (c1, c2) -> c1.getName().compareTo(c2.getName()))
		.forEach(System.out::println);

		// Print all Spanish painters, sorted by name (method reference for Comparator)
		PainterData.painters.stream()
		.filter(c -> c.getNationality().equals(CountryCode.ES))
		.sorted( Comparator.comparing(Painter::getName) )
		.forEach(System.out::println);
		
	}

}
