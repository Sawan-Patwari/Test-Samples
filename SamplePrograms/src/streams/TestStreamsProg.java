package streams;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 
 * @author Sawan.Patwari
 *
 */
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
		optionalWithPrimitiveStreams();
		rangeCalculation();
		deferredExecution();
		streamsWithOptionals();
		checkedExcepWithFISample1();
		checkedExcepWithFISample2();
		checkedExcepWithFISample3();
		chainingOptionals();
		CollectorsSample.joining();
		CollectorsSample.averagingInt();
		CollectorsSample.toCollection();
		CollectorsSample.toMap();
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
		Stream.generate(() -> "1234").filter(n -> n.length() == 4).sorted().limit(2).forEach(System.out::println);

	}

	static void fixForNeverCallThisMethod1() {
		Stream.generate(() -> "1234").filter(n -> n.length() == 4).limit(2).sorted().forEach(System.out::println);
	}

	static void neverCallThisMethod2() {
		/*
		 * This one hangs as well until we kill the program. The filter doesn’t allow
		 * anything through, so limit() never sees two elements. This means that we have
		 * to keep waiting and hope that they show up.
		 */
		Stream.generate(() -> "123456").filter(n -> n.length() == 4).limit(2).sorted().forEach(System.out::println);

	}

	static void streamIterate() {
		Stream<Integer> infinite = Stream.iterate(1, x -> x + 1);
		infinite.filter(x -> x % 2 == 1).limit(5).forEach(System.out::print);
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
		System.out.println(stream.collect(Collectors.toList()));
	}

	static void printStreamDiffWays2() {
		System.out.println("************");
		System.out.println("Method Name:" + "printStreamDiffWays2");
		// Stream<String> stream = Stream.of("1","2","3","4");//didn't work.
		Stream<List<?>> stream = getStreamSample();// didn't work.
		stream.peek(System.out::println).count();// didn't work.
	}

	static void printStreamDiffWays3() {
		System.out.println("************");
		System.out.println("Method Name:" + "printStreamDiffWays3");
		Stream<String> stream = Stream.of("1", "2", "3", "4");
		stream.limit(5).forEach(System.out::println);
	}

	static Stream<List<?>> getStreamSample() {
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
		/*
		 * Three types of primitive streams: IntStream : Used for the primitive types
		 * int , short , byte , and char LongStream : Used for the primitive type long
		 * DoubleStream : Used for the primitive types double and float
		 */
		DoubleStream oneValue = DoubleStream.of(18.90);
		DoubleStream varargs = DoubleStream.of(2.0, 2.1, 1.2);
		oneValue.forEach(System.out::println);
		System.out.println(oneValue);
		// System.out.println(ToStringBuilder.reflectionToString(oneValue));
		varargs.forEach(System.out::println);
		DoubleStream empty = DoubleStream.empty();

		DoubleStream random = DoubleStream.generate(Math::random);
		DoubleStream fractions = DoubleStream.iterate(.5, d -> d / 3);
		random.limit(3).forEach(System.out::println);
		System.out.println();
		fractions.limit(3).forEach(System.out::println);

		System.out.println("IntStream.iterate(1, n -> n+1):");
		IntStream intStream1 = IntStream.iterate(1, n -> n + 1).limit(5);
		intStream1.forEach(System.out::println);

		System.out.println("IntStream.range(1, 10):");
		IntStream range = IntStream.range(1, 10);
		range.forEach(System.out::println);

		System.out.println("IntStream.rangeClosed(1, 5):");
		IntStream rangeClosed = IntStream.rangeClosed(1, 5);
		rangeClosed.forEach(System.out::println);

		Stream<String> objStream = Stream.of("123", "22");
		IntStream intStream3 = objStream.mapToInt(s -> s.length());

		List<String> list = Arrays.asList("123", "22");
		IntStream ints = list.stream().flatMapToInt(x -> IntStream.of(Integer.valueOf(x)));
		// java.util.stream.ReferencePipeline
		System.out.println(ints);// Eclipse not providing interface implementer classes list.
		ints.forEach(System.out::println);

	}

	static void optionalWithPrimitiveStreams() {
		System.out.println("************");
		System.out.println("Method Name:" + "optionalWithPrimitiveStreams");
		IntStream stream = IntStream.rangeClosed(1, 20);
		OptionalDouble optional = stream.average();
		optional.ifPresent(System.out::println);
		System.out.println(optional.getAsDouble());
		System.out.println(optional.orElseGet(() -> Double.NaN));

		LongStream longs = LongStream.of(15, 10);
		long sum = longs.sum();
		System.out.println(sum); // 15

		// OptionalDouble OptionalInt OptionalLong
	}

	/**
	 * IntSummaryStatistics includes the following calculations - minimum, maximum,
	 * average, size, and the number of values in the stream. Helpful because We
	 * can’t run two terminal operations against the same stream.
	 */
	static void rangeCalculation() {
		System.out.println("************");
		System.out.println("Method Name:" + "rangeCalculation");
		IntStream instream = IntStream.of(15, 10, 20, 1, 29, 91);
		IntSummaryStatistics stats = instream.summaryStatistics();
		System.out.println(stats.getMax() - stats.getMin());
	}
	
	static void deferredExecution() {
		System.out.println("************");
		System.out.println("Method Name:" + "deferredExecution");
		List<String> counter = new ArrayList<>();
		counter.add("1");
		counter.add("2");
		
		Stream<String> stream = counter.stream();
		counter.add("3");
		counter.add("4");
		System.out.println(stream.count());
		counter.add("5");
		try{
			System.out.println(stream.count());// a terminal operation.
		}catch(java.lang.IllegalStateException e) {System.out.println(e);}
	}
	
	static void streamsWithOptionals() {
		Optional<Integer> optional = Optional.of(1234);
		printIf4DigitRegular(optional);
		printIf4DigitWithStreams(optional);
		
		Optional<String> optional1 = optional.map(i -> ""+i);
		Optional<Integer> result = optional1.map(String::length);
		result.ifPresent(t-> System.out.println("result:"+t));
		
	}
	
	static void printIf4DigitRegular(Optional<Integer> optional) {
		if (optional.isPresent()) {

			Integer num = optional.get();
			String string = "" + num;
			if (string.length() == 4)

				System.out.println(string);
		}
	}
	
	static void printIf4DigitWithStreams(Optional<Integer> optional) {
		optional.map(n -> "" + n)
		.filter(s -> s.length() == 4)
		.ifPresent(System.out::println);
	}
	
	static void checkedExcepWithFISample1() {
		
		class CheckedExcepWithFI{
			public List<String> testMethod() throws FileNotFoundException {
				throw new FileNotFoundException();
			}
		}
		
		Supplier<List<String>> supplierInst = () -> {
			try {
				
				return new CheckedExcepWithFI().testMethod();
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		};
		
		try {
			supplierInst.get();
		}catch(RuntimeException e) {
			System.out.println(e);
		}
		
	}
	
	//Not having dynamic line number to have clean syntax for functional programming code.
	static void checkedExcepWithFISample2() {
		
		class CheckedExcepWithFI {
			
			{		
				System.out.println("Line Number:" + Thread.currentThread().getStackTrace()[1].getLineNumber() +
						" Debugging "+this.getClass().getEnclosingMethod()+"(): declaring an inner class - CheckedExcepWithFI inside the method.");
			}					

			public List<String> testMethod() throws FileNotFoundException {
				throw new FileNotFoundException();
			}

			public List<String> getSupplier() {
				try {
					return testMethod();
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		}

		Supplier<List<String>> supplierInst = () -> {
			try {

				return new CheckedExcepWithFI().testMethod();
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		};

		try {
			supplierInst.get();
		} catch (RuntimeException e) {
			System.out.println(e);
		}

		Supplier<List<String>> supplierInst1 = () -> {
			System.out.println("Debugging [Line Number]:"+Thread.currentThread().getStackTrace()[1].getLineNumber() +": Block code test.");
			return new CheckedExcepWithFI().getSupplier();
		};

		{
		} // empty block code. Tested the syntax.

		// Also:
		Supplier<List<String>> supplierInst2 = () -> new CheckedExcepWithFI().getSupplier();

		try {
			supplierInst1.get();
		} catch (RuntimeException e) {
			System.out.println(e);
		}

		try {
			supplierInst2.get();
		} catch (RuntimeException e) {
			System.out.println(e);
		}

	}
		
	static void checkedExcepWithFISample3() {
		
		class CheckedExcepWithFI {
			
			{		
				System.out.println("Line Number:" + Thread.currentThread().getStackTrace()[1].getLineNumber() +
						" Debugging "+this.getClass().getEnclosingMethod()+"(): declaring an inner class - CheckedExcepWithFI inside the method.");
			}		
			
			public CheckedExcepWithFI(int lineNumber, String instanceVariableNm) {
				System.out.println("Line Number: " + lineNumber
						+ " Instance Variable Declared Name: " + instanceVariableNm
						);
			}

			public List<String> testMethod() throws FileNotFoundException {
				throw new FileNotFoundException();
			}

			public List<String> getSupplier() {
				try {
					return testMethod();
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		}

		Supplier<List<String>> supplierInst = () -> {
			try {

				return new CheckedExcepWithFI(Thread.currentThread().getStackTrace()[1].getLineNumber(), "supplierInst").testMethod();
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		};

		try {
			supplierInst.get();
		} catch (RuntimeException e) {
			System.out.println(e);
		}

		Supplier<List<String>> supplierInst1 = () -> {
			System.out.println("Debugging [Line Number]:"+Thread.currentThread().getStackTrace()[1].getLineNumber() +": Block code test.");
			return new CheckedExcepWithFI(Thread.currentThread().getStackTrace()[1].getLineNumber(), "supplierInst1").getSupplier();
		};

		{
		} // empty block code. Tested the syntax.

		// Also:
		Supplier<List<String>> supplierInst2 = () -> new CheckedExcepWithFI(Thread.currentThread().getStackTrace()[1].getLineNumber(), "supplierInst3").getSupplier();

		try {
			supplierInst1.get();
		} catch (RuntimeException e) {
			System.out.println(e);
		}

		try {
			supplierInst2.get();
		} catch (RuntimeException e) {
			System.out.println(e);
		}

	}
	
	static class ChainingOptionals {// static class because there is at least one static method.
		public static Optional<Integer> computeLength(String s) {
			if(s!= null) {
				return Optional.of(s.length());
			}else {
				return Optional.of(0);
			}
		}

	}
	
	static void chainingOptionals() {
		System.out.println("Inside chainingOptionals().");
		//static class ChainingOptionals {//Not permitted within a method.
		class ChainingOptionals1 {// local classes cannot be static.
			//public static Optional<Integer> computeLength(String s) { //Cannot do this.
			public Optional<Integer> computeLength(String s) {
				if(s!= null) {
					return Optional.of(s.length());
				}else {
					return Optional.of(0);
				}
			}

		}
		Optional<String> name = Optional.of("Sawan.Patwari");
		
		Optional<Integer> nameLength1 = name.flatMap(
				ChainingOptionals::computeLength);
		nameLength1.ifPresent(l -> System.out.println(l));
		
		Optional<Integer> nameLength2 = name.flatMap(
				s -> new ChainingOptionals1().computeLength(s)				
				);
		nameLength2.ifPresent(l -> System.out.println(l));	
		
		/*
		 * Note: The map() method adds another Optional , giving us
		 * Optional<Optional<Integer>>; thus, have to use flatMap.
		 * 
		 */
	}
	
	static class CollectorsSample {
		
		static void joining() {
			System.out.println("Inside the method: CollectorsSample.joining()");
			Stream<String> streamSample = Stream.of("1", "2", "3");
			String result = streamSample.collect(Collectors.joining(", "));
			System.out.println(result);
			/*Similar functions:
			 * 	Collectors.joining()
			 */
		}
		
		static void averagingInt() {
			Stream<String> streamSample = Stream.of("Mango", "Banana", "Pineapple");
			Double result = streamSample.collect(Collectors.averagingInt(String::length));
			System.out.println(result);
			
			/*Similar functions from Collectors:
			 * 	averagingDouble(ToDoubleFunction f); averagingLong(ToLongFunction f)
			 */
		}
		
		static void toCollection() {
			Stream<String> streamSample = Stream.of("titan", "tigers", "bears", "tennis");
			TreeSet<String> result = streamSample.filter(s -> s.startsWith("t"))
			.collect(Collectors.toCollection(TreeSet::new));
			System.out.println(result);
			
			/*
			 * Note: Also check Collectors.toList() and Collectors.toSet()
			 */
		}
		
		static void toMap() {
			Stream<String> streamSample = Stream.of("titan", "tigers", "bears", "tennis");
			Map<String, Integer> map = streamSample.collect(
			Collectors.toMap(s -> s, String::length));
			System.out.println(map);
			
			Stream<String> streamSample1 = Stream.of("titan", "tigers", "bears", "tennis");
			Map<String, Integer> map1 = streamSample1.collect(
			Collectors.toMap(Function.identity(), String::length));
			System.out.println(map1);
			
			System.out.println("streamSample2:");
			Stream<String> streamSample2 = Stream.of("titan", "tigers", "bears", "tennis", "edge");
			Map<Integer, String> map2 = streamSample2.collect(Collectors.toMap(
			String::length, k -> k, (s1, s2) -> s1 + "," + s2));
			System.out.println(map2); 
			
			Stream<String> streamSample3 = Stream.of("lions", "tigers", "bears");
			TreeMap<Integer, String> map3 = streamSample3.collect(Collectors.toMap(
			String::length, k -> k, (s1, s2) -> s1 + "," + s2, TreeMap::new));
			System.out.println(map3);
			
			/*
			 * Note: Take a look at the toMap APIs' function definitions.
			 */
			
		}
		
		
		
	}
	

}

//**********************
//Notes: [Start]
//**********************

/* Note-1:
 * Functional Interfaces for Primitives: BooleanSupplier b1 = () -> true;
 * BooleanSupplier b2 = () -> Math.random() > .5;
 * System.out.println(b1.getAsBoolean()); System.out.println(b2.getAsBoolean());
 * 
 * DoubleSupplier IntSupplier LongSupplier DoubleConsumer IntConsumer
 * LongConsumer DoublePredicate IntPredicate LongPredicate DoubleFunction<R>
 * IntFunction<R> LongFunction<R> DoubleUnaryOperator IntUnaryOperator
 * LongUnaryOperator DoubleBinaryOperator IntBinaryOperator LongBinaryOperator
 */

/* Note-2:
 * Primitive-specific functional interfaces: ToDoubleFunction<T>
 * ToIntFunction<T> ToLongFunction<T> ToDoubleBiFunction<T, U>
 * ToIntBiFunction<T, U> ToLongBiFunction<T, U> DoubleToIntFunction
 * DoubleToLongFunction IntToDoubleFunction IntToLongFunction
 * LongToDoubleFunction LongToIntFunction ObjDoubleConsumer<T> ObjIntConsumer<T>
 * ObjLongConsumer<T>
 * 
 * Example:
 * 
 * double d = 2.0; DoubleToIntFunction func = x -> 2; func.applyAsInt(d);
 */


//**********************
//Notes: [End]
//**********************
