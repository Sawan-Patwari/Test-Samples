package test.collections;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class TreeSetProg {

	private static TreeSetProg sample = new TreeSetProg();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test2();
	}

	/*
	 * Exception in thread "main" java.lang.ClassCastException:
	 * test.collections.TreeSetProg$Employee cannot be cast to
	 * java.base/java.lang.Comparable at java.base/java.util.TreeMap.compare(Unknown
	 * Source)
	 */
	public static void test1() {
		Employee x = sample.new Employee();
		x.a = 10;
		x.b = 20;

		Employee x1 = sample.new Employee();
		x1.a = 10;
		x1.b = 20;

		if (x.equals(x1)) {
			System.out.println("Objects equal");
		} else {
			System.out.println("Objects not equal");
		}

		Set<Employee> setSample = new TreeSet<>();
		setSample.add(x);
		setSample.add(x1);
		System.out.println(setSample.size());

	}

	// Set sample
	public static void test1_fix() {
		Employee_Fix x = sample.new Employee_Fix();
		x.a = 10;
		x.b = 20;

		Employee_Fix x1 = sample.new Employee_Fix();
		x1.a = 10;
		x1.b = 20;

		if (x.equals(x1)) {
			System.out.println("Objects equal");
		} else {
			System.out.println("Objects not equal");
		}

		Set<Employee_Fix> setSample = new TreeSet<>();
		setSample.add(x);
		setSample.add(x1);
		System.out.println(setSample.size());

	}
	
	//Made the TreeSet to contain duplicate elements; duplicate elements as per our terminology.
	public static void test2() {
		Employee_Fix_1 x = sample.new Employee_Fix_1();
		x.a = 10;
		x.b = 20;

		Employee_Fix_1 x1 = sample.new Employee_Fix_1();
		x1.a = 10;
		x1.b = 20;
		
		Employee_Fix_1 x2 = sample.new Employee_Fix_1();
		x2.a = 10;
		x2.b = 20;

		Set<Employee_Fix_1> setSample = new TreeSet<>();
		setSample.add(x);
		setSample.add(x1);
		setSample.add(x2);
		System.out.println(setSample.size());
		
		setSample.forEach((m) -> {System.out.println(m.a + " " + m.b);});

	}

	class Employee {
		int a;
		int b;

	}

	class Employee_Fix implements Comparable<Employee_Fix> {
		int a;
		int b;

		@Override
		public int compareTo(Employee_Fix o) {
			// TODO Auto-generated method stub

			if (this.equals(o)) {
				return 0;
			} else {
				return 1;
			}

		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub

			// simple one line code:
			// return EqualsBuilder.reflectionEquals(this, (Employee3)obj);

			// better improved code:
			Employee_Fix other = (Employee_Fix) obj;

			if (this == other) {
				return true;
			} else {

				// Simple code:
				// return EqualsBuilder.reflectionEquals(this, other);

				// For the better performance of the equal's method.
				if (this.hashCode() == other.hashCode()) {
					return EqualsBuilder.reflectionEquals(this, other);
				} else {
					return false;
				}

			}

		}

		// Not necessary to implement, unlike in the case of HashMap, however, it is
		// better for the better performance of the equal's method only.
		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

	}

	class Employee_Fix_1 implements Comparable<Employee_Fix_1> {
		int a;
		int b;

		@Override
		public int compareTo(Employee_Fix_1 o) {
			// TODO Auto-generated method stub

			return 1;

		}
	}

}
