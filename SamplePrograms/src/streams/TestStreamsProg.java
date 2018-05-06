package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStreamsProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Below function calls will increase the fan speed drastically.
		// Avoid running such code.
		// randomNumGen();
		// iterate();
		streamCounting();
		streamMin();
		streamMax();
		streamEmpty();
		firstFindAny();
		matchSamples();
		streamForEach();
		streamReduce();
		streamCollector();
		streamFilters();
		streamDistinct();
		streamSkipAndLimit();
		streamMap();
		streamFlatMap();
		streamSort();
	}

	static void randomNumGen() {
		Stream<Double> randoms = Stream.generate(Math::random);
		randoms.forEach(System.out::println);
	}

	static void iterate() {
		Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);
		oddNumbers.forEach(System.out::println);
	}

	static void streamCounting() {
		Stream<String> s = Stream.of("monkeys", "gorillas", "bonobos");
		// 3
		System.out.println(s.count());
	}

	static void streamMin() {
		Stream<String> s = Stream.of("bonobo", "monkeys", "apes");
		Optional<String> min = s.min((s1, s2) -> s1.length() - s2.length());
		min.ifPresent(System.out::println);
	}

	static void streamMax() {
		Stream<String> s = Stream.of("bonobo", "monkeys", "apes");
		Optional<String> max = s.max((s1, s2) -> s1.length() - s2.length());
		max.ifPresent(System.out::println);
	}

	static void streamEmpty() {
		Optional<?> minEmpty = Stream.empty().min((s1, s2) -> 0);
		// false
		System.out.println(minEmpty.isPresent());
	}

	static void firstFindAny() {
		Stream<String> s = Stream.of("monkeys", "gorillas", "bonobos");
		s.findAny().ifPresent(System.out::println);
	}

	static void matchSamples() {
		List<String> list = Arrays.asList("monkey", "2", "chimp");
		Stream<String> infinite = Stream.generate(() -> "chimp");
		Predicate<String> pred = x -> Character.isLetter(x.charAt(0));
		System.out.println(list.stream().anyMatch(pred)); // true
		System.out.println(list.stream().allMatch(pred)); // false
		System.out.println(list.stream().noneMatch(pred)); // false
		Stream<String> infinite1 = infinite;

		System.out.println(infinite.anyMatch(pred)); // true
		// java.lang.IllegalStateException for the below line since
		// 'infinite.anyMatch(pred)' (the previous operation)
		// is a terminal stream operations.
		try {
			System.out.println(infinite.allMatch(pred));
		} catch (IllegalStateException e) {
			System.out.println("Exception caught");
		}
		try {
			System.out.println(infinite1.allMatch(pred));
		} catch (IllegalStateException e) {
			System.out.println("Exception caught again because infinite1 = infinite");
		}

		Stream.Builder<String> sb = Stream.builder();
		sb.add("My");
		sb.add("name");
		sb.add("is");
		sb.add("Sawan");
		sb.build();

	}
	
	static void streamForEach() {
		Stream<String> s = Stream.of("Monkeys\t", "Gorillas\t", "Bonobos");
		// MonkeyGorillaBonobo
		s.forEach(System.out::print);
	}

	static void streamReduce() {
		String[] array = new String[] { "s", "a", "w", "a", "n" };
		String result = "";
		for (String s: array) result = result + s;
		System.out.println(result);
		
		
		Stream<String> stream = Stream.of("s", "a", "w", "a", "n" );
		String word = stream.reduce("", (s, c) -> s + c);
		System.out.println(word);
		try {
			String word1 = stream.reduce("", String::concat);
			System.out.println(word1);
		} catch (IllegalStateException e) {
			System.out.println("IllegalStateException Exception caught again.");
		}
		
		Stream<Integer> stream3 = Stream.of(3, 5, 6);
		System.out.println(stream3.reduce(1, (a, b) -> a*b));
		
		Stream<Integer> stream4 = Stream.of(3, 5, 6);
		System.out.println(stream4.reduce(0, (a, b) -> a+b));
		
		BinaryOperator<Integer> op1 = (a, b) -> a * b;
		Stream<Integer> empty = Stream.empty();
		Stream<Integer> oneElement = Stream.of(3);
		Stream<Integer> threeElements = Stream.of(3, 5, 6);
		empty.reduce(op1).ifPresent(System.out::print); // no output
		oneElement.reduce(op1).ifPresent(System.out::print); // 3
		threeElements.reduce(op1).ifPresent(System.out::print); // 90
		
		BinaryOperator<Integer> op = (a, b) -> a * b;
		Stream<Integer> stream1 = Stream.of(3, 5, 6);
		System.out.println(stream1.reduce(1, op, op)); // 90
		
	}
	
	static void streamCollector() {
		Stream<String> stream = Stream.of("s", "a", "w", "a", "n");
		StringBuilder word = stream.collect(StringBuilder::new,
		StringBuilder::append, StringBuilder::append);
		System.out.println(word);
		
		Stream<String> stream2 = Stream.of("s", "a", "w", "a", "n");
		TreeSet<String> set = stream2.collect(TreeSet::new, TreeSet::add,
		TreeSet::addAll);
		System.out.println(set);
		
		Stream<String> stream4 = Stream.of("s", "a", "w", "a", "n");
		TreeSet<String> set2 = stream4.collect(Collectors.toCollection(TreeSet::new));
		System.out.println(set2);
		
		Stream<String> stream5 = Stream.of("w", "o", "l", "f");
		Set<String> set5 = stream5.collect(Collectors.toSet());
		System.out.println(set5);
	}
	
	static void streamFilters() {
		Stream<String> s = Stream.of("donkey", "godzilla", "bonobo");
		s.filter(x -> x.startsWith("d")).forEach(System.out::println);
		// donkey
		
	}
	
	static void streamDistinct() {
		Stream<String> s = Stream.of("Luck", "Luck", "Luck", "donkey");
		s.distinct().forEach(System.out::println); 
		
	}
	
	static void streamSkipAndLimit() {
		Stream<Integer> s = Stream.iterate(1, n -> n + 1);
		
		s.skip(5).limit(2).forEach(System.out::println);
	}
	
	static void streamMap() {
		Stream<String> s = Stream.of("Luck1", "Luck12", "Luck234", "donkey");
		
		System.out.println("streamMap");
		s.map(String::length).forEach(System.out::println);
	}
	
	/*
	 The flatMap() method takes each element in the stream and makes any elements it con-
	 tains top-level elements in a single stream. This is helpful when you want to remove empty
	 elements from a stream or you want to combine a stream of lists.
	 */
	static void streamFlatMap() {
		System.out.println("Method Name:" + "streamFlatMap" );
		List<String> zero = Arrays.asList();
		List<String> one = Arrays.asList("Yakku");
		List<String> two = Arrays.asList("Yakku Makku", "Vakku Chokku");
		Stream<List<String>> animals = Stream.of(zero, one, two);
		animals.flatMap(l -> l.stream()).forEach(System.out::println);
		//animals.flatMap(l -> Stream.of("A")).forEach(System.out::println);
	}
	
	static void streamSort() {
		Stream<String> s = Stream.of("white", "black");
		s.sorted().forEach(System.out::println);
		
		Stream<String> s1 = Stream.of("white", "black");
		s1.sorted(Comparator.reverseOrder())
		.forEach(System.out::println);
	}
}
