package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
		streamPeek();
		streamPeek1();
		streamPiplining();
		fixForNeverCallThisMethod1();
		streamIterate();
		printStreamDiffWays();
		printStreamDiffWays1();
		printStreamDiffWays2();
		printStreamDiffWays3();
		streamIntPrimitives();
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
		for (String s : array)
			result = result + s;
		System.out.println(result);

		Stream<String> stream = Stream.of("s", "a", "w", "a", "n");
		String word = stream.reduce("", (s, c) -> s + c);
		System.out.println(word);
		try {
			String word1 = stream.reduce("", String::concat);
			System.out.println(word1);
		} catch (IllegalStateException e) {
			System.out.println("IllegalStateException Exception caught again.");
		}

		Stream<Integer> stream3 = Stream.of(3, 5, 6);
		System.out.println(stream3.reduce(1, (a, b) -> a * b));

		Stream<Integer> stream4 = Stream.of(3, 5, 6);
		System.out.println(stream4.reduce(0, (a, b) -> a + b));

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
		StringBuilder word = stream.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
		System.out.println(word);

		Stream<String> stream2 = Stream.of("s", "a", "w", "a", "n");
		TreeSet<String> set = stream2.collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
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
	 * The flatMap() method takes each element in the stream and makes any elements
	 * it con- tains top-level elements in a single stream. This is helpful when you
	 * want to remove empty elements from a stream or you want to combine a stream
	 * of lists.
	 */
	static void streamFlatMap() {
		System.out.println("Method Name:" + "streamFlatMap");
		List<String> zero = Arrays.asList();
		List<String> one = Arrays.asList("Yakku");
		List<String> two = Arrays.asList("Yakku Makku", "Vakku Chokku");
		Stream<List<String>> animals = Stream.of(zero, one, two);
		animals.flatMap(l -> l.stream()).forEach(System.out::println);
		// animals.flatMap(l -> Stream.of("A")).forEach(System.out::println);
	}

	static void streamSort() {
		Stream<String> s = Stream.of("white", "black");
		s.sorted().forEach(System.out::println);

		Stream<String> s1 = Stream.of("white", "black");
		s1.sorted(Comparator.reverseOrder())// ambiguity
				.forEach(System.out::println);
		/*
		 * Take a look at the method signatures again. Comparator is a functional inter-
		 * face. This means that we can use method references or lambdas to implement
		 * it. The Comparator interface implements one method that takes two String
		 * parameters and returns an int . However, Comparator::reverseOrder doesn’t do
		 * that. It is a reference to a function that takes zero parameters and returns
		 * a Comparator . This is not com- patible with the interface. This means that
		 * we have to use a method and not a method reference.
		 * 
		 */
	}

	static void streamPeek() {
		Stream<String> stream = Stream.of("junk1", "junk2", "junk3", "junk33", "junk34");
		long count = stream.filter(s -> s.startsWith("junk3")).peek(System.out::println).count();
		System.out.println(count);
		/*
		 * When working with a Queue , peek() looks only at the first element. In a
		 * stream, peek() looks at each element that goes through that part of the
		 * stream pipeline. It’s like having a worker take notes on how a particular
		 * step of the process is doing.
		 */
	}

	static void streamPeek1() {
		System.out.println("************");
		System.out.println("Method Name:" + "streamPeek1");
		List<Integer> numbers = new ArrayList<>();
		List<Character> letters = new ArrayList<>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		letters.add('a');
		letters.add('b');
		letters.add('c');
		letters.add('d');
		Stream<List<?>> stream = Stream.of(numbers, letters);
		stream.map(List::size).forEach(System.out::print);
		System.out.println("************");

		StringBuilder builder = new StringBuilder();
		Stream<List<?>> good = Stream.of(numbers, letters);
		good.peek(l -> builder.append(l)).map(List::size).forEach(System.out::print);
		System.out.println(builder);

		System.out.println("************");
		Stream<List<?>> inputStream = Stream.of(numbers, letters);
		inputStream.peek(l -> l.remove(0)).map(List::size).forEach(System.out::print);

		System.out.println("************");
		StringBuilder builder1 = new StringBuilder();
		Stream<List<?>> inputStream1 = Stream.of(numbers, letters);
		inputStream1.peek(l -> {
			l.remove(0);
			builder1.append(l);
		}).map(List::size).forEach(System.out::print);
		System.out.println(builder1);

		System.out.println("************");

		Stream<Integer> infiniteSeries = Stream.iterate(1, x -> x + 1);
		infiniteSeries.limit(7).peek(System.out::print).filter(x -> x % 2 == 1).forEach(System.out::print);

		System.out.println("************");
	}

	static void streamPiplining() {
		System.out.println("************");
		System.out.println("Method Name:" + "streamPiplining");
		List<String> list = Arrays.asList("Magna", "Maga", "Mera", "Mighty");
		list.stream().filter(n -> n.length() == 4).sorted().limit(2).forEach(System.out::println);
		System.out.println("************");
	}

	static void neverCallThisMethod() {
		Stream.generate(() -> "1234").filter(n -> n.length() == 4).sorted().limit(2).forEach(System.out::println);
	}

	static void neverCallThisMethod1() {
		/*
		 * It actually hangs until you kill the program or it throws an exception after
		 * running out of memory. The foreman has instructed sorted() to wait until
		 * everything to sort is present. That never happens because there is an
		 * infinite stream.
		 * 
		 */
		Stream.generate(() -> "1234").filter(n -> n.length() == 4).
										sorted().
										limit(2).
										forEach(System.out::println);
		
	}
	
	static void fixForNeverCallThisMethod1() {
		Stream.generate(() -> "1234")
		.filter(n -> n.length() == 4)
		.limit(2)
		.sorted()
		.forEach(System.out::println);
	}
	
	static void neverCallThisMethod2() {
		/*
		 *  This one hangs as well until we kill the program. The filter doesn’t allow anything
			through, so limit() never sees two elements. This means that we have to keep waiting and
			hope that they show up.
		 */
		Stream.generate(() -> "123456")
		.filter(n -> n.length() == 4)
		.limit(2)
		.sorted()
		.forEach(System.out::println);
		
	}
	
	static void streamIterate() {
		Stream<Integer> infinite = Stream.iterate(1, x -> x + 1);
		infinite.filter(x -> x % 2 == 1)
		.limit(5)
		.forEach(System.out::print);
	}
	
	static void printStreamDiffWays() {
		System.out.println("************");
		System.out.println("Method Name:" + "printStreamDiffWays");
		Stream<List<?>> stream = getStreamSample();
		stream.forEach(System.out::println);		
	}
	
	static void printStreamDiffWays1() {
		System.out.println("************");
		System.out.println("Method Name:" + "printStreamDiffWays1");
		Stream<List<?>> stream = getStreamSample();
		System.out.println(stream.collect(Collectors.
				toList()));		
	}
	
	static void printStreamDiffWays2() {
		System.out.println("************");
		System.out.println("Method Name:" + "printStreamDiffWays2");
		//Stream<String> stream = Stream.of("1","2","3","4");//didn't work.
		Stream<List<?>> stream = getStreamSample();//didn't work.
		stream.peek(System.out::println).count();//didn't work.		
	}
	
	static void printStreamDiffWays3() {
		System.out.println("************");
		System.out.println("Method Name:" + "printStreamDiffWays3");
		Stream<String> stream = Stream.of("1","2","3","4");
		stream.limit(5).forEach(System.out::println);
	}
	
	static Stream<List<?>> getStreamSample(){
		List<Integer> numbers = new ArrayList<>();
		List<Character> letters = new ArrayList<>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		letters.add('a');
		letters.add('b');
		letters.add('c');
		letters.add('d');
		Stream<List<?>> stream = Stream.of(numbers, letters);
		
		return stream;
	}
	
	static void streamIntPrimitives() {
		System.out.println("************");
		System.out.println("Method Name:" + "streamIntPrimitives");
		Stream<Integer> stream = Stream.of(1, 2, 3);
		System.out.println(stream.reduce(0, (s, n) -> s + n));
		
		Stream<Integer> stream2 = Stream.of(1, 2, 3);
		System.out.println(stream2.mapToInt(x -> x).sum());
		
		IntStream intStream = IntStream.of(1, 2, 3);
		OptionalDouble avg = intStream.average();
		System.out.println(avg.getAsDouble());
		
	}

}
