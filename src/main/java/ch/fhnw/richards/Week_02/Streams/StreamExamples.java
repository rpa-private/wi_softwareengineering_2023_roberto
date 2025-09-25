package ch.fhnw.richards.Week_02.Streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExamples {

	public static void main(String[] args) {
		// Stream of Strings
		Stream<String> s1 = Stream.of("This", "is", "a", "stream");
		String sentence = s1.collect(Collectors.joining(" "));
		System.out.println(sentence);
		
		// Stream of primitive data types
		int[] primes = {2, 3, 5, 7, 11, 13};
		IntStream s2 = Arrays.stream(primes);
		int sum = s2.sum();
		System.out.println(sum);
		
		// Dynamically produce a (potentially infinite) stream of values
		// In this case, the odd integers. "Lazy evaluation" means that
		// elements are generated on demand
		Stream<Integer> s3 = Stream.iterate(1, n -> n+2);
		s3.limit(10) // but here we only want the first 10 values
		  .forEach(System.out::println);
		
		// How about the smallest from a bunch of random numbers?
		// Note: comes wrapped in an Optional, in case the stream
		// was empty
		Stream<Double> s4 = Stream.generate(Math::random);
		Optional<Double> smallest = s4.limit(5).min(Comparator.naturalOrder());
		if (smallest.isPresent()) System.out.println(smallest.get());
		
		// Sequence of integers: 1, 2, 2, 3, 3, 3, 4, 4, 4, 4
		// From: https://stackoverflow.com/questions/22382453/java-8-streams-flatmap-method-example/22384113#22384113
		IntStream s5 = IntStream.rangeClosed(1, 4)
                .flatMap(i -> IntStream.iterate(i, IntUnaryOperator.identity()).limit(i));
		s5.forEach(System.out::println);
	}

}
