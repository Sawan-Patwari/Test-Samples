package test.lambda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/** 
 * @author Sawan.Patwari
 *
 */
public class LambdaProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SampleLambdaProg lambdaProg = new SampleLambdaProg();
		lambdaProg.doLambdaAPISample();
		lambdaProg.doSupplierSample();
		lambdaProg.doConsumerSample();
		lambdaProg.doBiConsumerSample();
		lambdaProg.doPredicateSample();
		lambdaProg.doBiPredicateSample();
		lambdaProg.doFIDefaultMethodsSample();
		lambdaProg.doFuntionSample();
		lambdaProg.doBiFuntionSample();
		lambdaProg.doUnaryOperatorSample();
		lambdaProg.doBinaryOperatorSample();
		lambdaProg.doOptionalsSample();
	}
}

class SampleLambdaProg {
	
	@FunctionalInterface
	interface Lambda<T>{
		T provideDisplayValue();
	}
	
	/**
	 * A Lambda API.
	 * @param provider
	 */
	private void displayValue(Lambda<? extends Object> provider) {
		System.out.println(provider.provideDisplayValue());	
	}
	
	public void doLambdaAPISample() {
		
		Lambda<Integer> intl = () -> {return Integer.valueOf(1);};
		Lambda<String>	stringl = () -> {return "Hi";};
		Lambda<Double> doublel = () -> {return 2.0;};
		
		displayValue(intl);
		displayValue(stringl);
		displayValue(doublel);
		
		displayValue(() -> {return 1.0f;});
		
	}
	
	public void doSupplierSample() {
				
		Supplier<LocalDate> dateSupplier1 = LocalDate::now;
		Supplier<LocalDate> dateSupplier2 = () -> LocalDate.now();
		LocalDate date1 = dateSupplier1.get();
		LocalDate date2 = dateSupplier2.get();
		System.out.println(date1);
		System.out.println(date2);
		
		Supplier<StringBuilder> stringBuilderSupplier1 = StringBuilder::new;
		Supplier<StringBuilder> stringBuilderSupplier2 = () -> new StringBuilder("Second string builder supplier.");
		
		StringBuilder firstStringBuilder = stringBuilderSupplier1.get().append("First string builder supplier.");
				
		System.out.println(firstStringBuilder);
		System.out.println(stringBuilderSupplier2.get());
		
		Supplier<List<String>> s1 = ArrayList<String>::new;
		List<String> ls1 = s1.get();
		ls1.add("First List First Value");
		ls1.add("First List Second Value");
		
		ArrayList<String> ls2 = (ArrayList<String>)s1.get();
		ls2.add("Second List First Value");
		ls2.add("Second List Second Value");
		
		System.out.println(ls1);
		System.out.println(ls2);
				
	}
	
	public void doConsumerSample() {
		Consumer<String> consumer1 = System.out::println;
		Consumer<String> consumer2 = consume -> System.out.println(consume);
		consumer1.accept("Name: Arjun");
		consumer2.accept("Name: Karan");
	}
	
	public void doBiConsumerSample() {
		Map<String, Integer> map = new HashMap<>();
		BiConsumer<String, Integer> b1 = map::put;
		BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);
		b1.accept("Name: Arjun", 1);
		b2.accept("Name: Karan", 2);
		System.out.println(map);
	}
	
	public void doPredicateSample() {
		Predicate<String> test1 = String::isEmpty;
		Predicate<String> test2 = x -> x.isEmpty();
		System.out.println(test1.test(""));
		System.out.println(test2.test("Filled"));
	}
	
	public void doBiPredicateSample() {
		BiPredicate<String, String> prefixTest1 = String::startsWith;
		BiPredicate<String, String> prefixTest2 = (string, prefix) -> string.startsWith(prefix);
		
		System.out.println(prefixTest1.test("Name: Arjun", "Name"));
		System.out.println(prefixTest2.test("Name: Karan", "Id"));
		
		BiPredicate<String, String> suffixTest1 = String::endsWith;
		BiPredicate<String, String> suffixTest2 = (string, suffix) -> string.endsWith(suffix);
		
		System.out.println(suffixTest1.test("Name: Arjun", "Arjun"));
		System.out.println(suffixTest2.test("Name: Karan", "Arjun"));
		
	}
	
	public void doFIDefaultMethodsSample() {
		
		System.out.println("doFIDefaultMethodsSample(): [Started]");
		
		Predicate<String> hasWhite = s -> s.contains("white");
		Predicate<String> hasBrown = s -> s.contains("brown");
		
		Predicate<String> hasBoth = hasWhite.and(hasBrown);
		Predicate<String> hasOnlyWhite = hasWhite.and(hasBrown.negate());
		Predicate<String> hasOnlyBrown = hasBrown.and(hasWhite.negate());
		Predicate<String> hasNeitherWhiteNorBrown = hasWhite.negate().and(hasBrown.negate());
		
		boolean testResult1 = hasBoth.test("white brown");
		boolean testResult2 = hasOnlyWhite.test("white bro_wnAbsent");
		boolean testResult3 = hasOnlyBrown.test("whi_teAbsent brown");
		boolean testResult4= hasNeitherWhiteNorBrown.test("whi_teAbsent bro_wnAbsent");
		
		System.out.println(testResult1);
		System.out.println(testResult2);
		System.out.println(testResult3);
		System.out.println(testResult4);
		
		System.out.println("doFIDefaultMethodsSample(): [Ended]");
	}
	
	public void doFuntionSample() {
		Function<String, Integer> f1 = String::length;
		Function<String, Integer> f2 = x -> x.length();
		System.out.println(f1.apply("Karan"));
		System.out.println(f2.apply("Arjun"));
	}
	
	public void doBiFuntionSample() {
		BiFunction<String, String, String> biFunction1 = String::concat;
		BiFunction<String, String, String> biFunction2 = (string1, string2) -> string1.concat(string2);
		System.out.println(biFunction1.apply("Karan ", "Arjun")); 
		System.out.println(biFunction2.apply("Arjun ", "Karan"));
	}
	
	public void doUnaryOperatorSample() {
		UnaryOperator<String> u1 = String::toUpperCase;
		UnaryOperator<String> u2 = x -> x.toUpperCase();
		System.out.println(u1.apply("Karan"));
		System.out.println(u2.apply("Arjun"));
	}
	
	public void doBinaryOperatorSample() {
		BinaryOperator<String> binaryOperator1 = String::concat;
		BinaryOperator<String> binaryOperator2 = (string1, string2) -> string1.concat(string2);
		
		System.out.println(binaryOperator1.apply("Karan ", "Arjun"));
		System.out.println(binaryOperator2.apply("Karan ", "Arjun"));
		
		System.out.println(
				BinaryOperator.maxBy(new Comparator<Integer>() {
		
					@Override
					public int compare(Integer x, Integer y) {
						// TODO Auto-generated method stub
						
						return x-y;
					}
					
				}).apply(10, 11)
		);
		
		//Reusable approach:		
		Comparator<Integer> integerComparator = new Comparator<Integer>() {
			
			@Override
			public int compare(Integer x, Integer y) {
				// TODO Auto-generated method stub
				
				return x-y;
			}
			
		};
		
		BinaryOperator<Integer> maxCheck = BinaryOperator.maxBy(integerComparator);
		
		BinaryOperator<Integer> minCheck = BinaryOperator.minBy(integerComparator);
		
		System.out.println(maxCheck.apply(80, 10));
		System.out.println(minCheck.apply(80, 10));
		
	}
	
	public void doOptionalsSample() {
		
		class Concatinate {
			
			public Optional<String> concat(String...strings) {
				
				if(strings == null || strings.length == 0) {
					return Optional.empty();
				}
				
				StringBuilder builder = new StringBuilder();
				
				for(String x : strings) {
					builder.append(x);
				}
				
				return Optional.of(builder.toString());
			}
		}
		
		Optional<String> result = new Concatinate().concat("A", "B","C");
		
		if(result.isPresent()) {
			System.out.println(result.get());
		}else {
			System.out.println(result.orElse("Showing Default Value"));
		}
		//Alternatively:
		Consumer<String> consume = x -> System.out.println(x);
		result.ifPresent(consume);
		
		Optional<String> result1 = new Concatinate().concat();
		System.out.println(result1.orElse("Default Value"));
		
		Supplier<String> defaultValue = () -> "Default Value"; 
		@SuppressWarnings("unused")
		Supplier<Integer> defaultIntValue = () -> 1; 
		System.out.println(result1.orElseGet(defaultValue));
		//Cannot write below code.
		//System.out.println(result1.orElseGet(defaultIntValue));
		
		try {
			result1.orElseThrow();
		}catch(Exception e) {
			System.out.println(e);
		}
		
		Supplier<Exception> exception = () -> new Exception("Throwing exception since the Optinal is empty");
		try {
			result1.orElseThrow(exception);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//do anything.		
			System.out.println(e);
		}
		
	}
}
