package ch.fhnw.richards.Week_02.Streams.painter_example;

import java.util.Arrays;
import java.util.List;

import ch.fhnw.richards.Week_02.Streams.painter_example.Painter.Gender;

/**
 * This class contains a list of famous painters,
 * taken from http://www.artcyclopedia.com/mostpopular.html
 */
public class PainterData {
	public static final List<Painter> painters = Arrays.asList(
			new Painter("Pablo Picasso", 1881, 1973, Gender.MALE, CountryCode.ES),
			new Painter("Vincent van Gogh", 1853, 1890, Gender.MALE, CountryCode.NL),
			new Painter("Leonardo da Vinci", 1452, 1519, Gender.MALE, CountryCode.IT),
			new Painter("Claude Monet", 1840, 1926, Gender.MALE, CountryCode.FR),
			new Painter("Salvador Dali", 1904, 1989, Gender.MALE, CountryCode.ES),
			new Painter("Henri Matisse", 1869, 1954, Gender.MALE, CountryCode.FR),
			new Painter("Rembrandt van Rijn", 1606, 1669, Gender.MALE, CountryCode.NL),
			new Painter("Andy Warhol", 1928, 1987, Gender.MALE, CountryCode.US),
			new Painter("Georgia O'Keeffe", 1887, 1986, Gender.FEMALE, CountryCode.US),
			new Painter("Michelangelo Buonarroti", 1475, 1564, Gender.MALE, CountryCode.IT),
			new Painter("Peter Paul Rubens", 1577, 1640, Gender.MALE, CountryCode.NL),
			new Painter("Edgar Degas", 1834, 1917, Gender.MALE, CountryCode.FR),
			new Painter("Michelangelo Merisi da Caravaggio", 1571, 1610, Gender.MALE, CountryCode.IT),
			new Painter("Pierre-Auguste Renoir", 1841, 1919, Gender.MALE, CountryCode.FR),
			new Painter("Rafael Sanzio de Urbino", 1483, 1520, Gender.MALE, CountryCode.IT),
			new Painter("Paul Cézanne", 1839, 1906, Gender.MALE, CountryCode.FR),
			new Painter("Marc Chagall", 1887, 1985, Gender.MALE, CountryCode.FR),
			new Painter("Tiziano Vecellio", 1485, 1576, Gender.MALE, CountryCode.IT),
			new Painter("Joan Miró", 1893, 1983, Gender.MALE, CountryCode.ES),
			new Painter("Jackson Pollock", 1912, 1956, Gender.MALE, CountryCode.US),
			new Painter("Gustav Klimt", 1862, 1918, Gender.MALE, CountryCode.AT),
			new Painter("Albrecht Dürer", 1471, 1528, Gender.MALE, CountryCode.DE),
			new Painter("Edward Hopper", 1882, 1967, Gender.MALE, CountryCode.US),
			new Painter("Wassily Kandinsky", 1866, 1944, Gender.MALE, CountryCode.FR),
			new Painter("Jan Vermeer", 1632, 1675, Gender.MALE, CountryCode.NL),
			new Painter("Paul Klee", 1879, 1940, Gender.MALE, CountryCode.CH),
			new Painter("Edvard Munch", 1863, 1944, Gender.MALE, CountryCode.NO),
			new Painter("Francisco de Goya", 1746, 1828, Gender.MALE, CountryCode.ES),
			new Painter("Janet Fish", 1938, Gender.FEMALE, CountryCode.US),
			new Painter("Edouard Manet", 1832, 1883, Gender.MALE, CountryCode.FR)
	);
}
