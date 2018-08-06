package test.collections;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class TreeMapProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test10();
	}

	public static void test1() {
		Map<String, String> sample = new TreeMap<>();
		sample.put("D", "1");
		sample.put("A", "2");
		sample.put("C", "3");
		sample.put("B", "4");

		System.out.println(sample);

	}

	/*
	 * Exception in thread "main" java.lang.ClassCastException:
	 * test.collections.Employee cannot be cast to java.base/java.lang.Comparable at
	 * java.base/java.util.TreeMap.compare(Unknown Source) at
	 * java.base/java.util.TreeMap.put(Unknown Source) at
	 * test.collections.TreeMapProg.test2(TreeMapProg.java:44) at
	 * test.collections.TreeMapProg.main(TreeMapProg.java:19)
	 */
	public static void test2() {

		Employee x = new Employee();
		x.a = 10;
		x.b = 20;

		Employee x1 = new Employee();
		x1.a = 10;
		x1.b = 20;

		Map<Employee, Employee> sample = new TreeMap<>();
		sample.put(x, x);
		sample.put(x1, x1);

		System.out.println(sample.size());
		System.out.println(sample);
	}

	public static void test3() {

		Employee_Fix x = new Employee_Fix();
		x.a = 10;
		x.b = 20;

		Employee_Fix x1 = new Employee_Fix();
		x1.a = 10;
		x1.b = 20;

		Map<Employee_Fix, Employee_Fix> sample = new TreeMap<>();
		sample.put(x, x);
		sample.put(x1, x1);

		System.out.println(sample.size());
		sample.forEach((m, n) -> {
			System.out.println(m.a + " " + n.b);
		});

	}

	public static void test4() {

		Employee_Fix x = new Employee_Fix();
		x.a = 10;
		x.b = 20;

		Employee_Fix x1 = new Employee_Fix();
		x1.a = 10;
		x1.b = 20;

		Employee_Fix x2 = new Employee_Fix();
		x2.a = 30;
		x2.b = 40;

		Employee_Fix x3 = new Employee_Fix();
		x3.a = 5;
		x3.b = 5;

		Map<Employee_Fix, Employee_Fix> sample = new TreeMap<>();
		sample.put(x, x);
		sample.put(x1, x1);
		sample.put(x2, x2);
		sample.put(x3, x3);

		System.out.println(sample.size());
		sample.forEach((m, n) -> {
			System.out.println(m.a + " " + n.b);
		});

	}

	public static void test5() {

		Employee_Fix x = new Employee_Fix();
		x.a = 10;
		x.b = 20;

		Employee_Fix x1 = new Employee_Fix();
		x1.a = 10;
		x1.b = 20;

		Employee_Fix x2 = new Employee_Fix();
		x2.a = 30;
		x2.b = 40;

		Employee_Fix x3 = new Employee_Fix();
		x3.a = 5;
		x3.b = 5;

		Employee_Fix x4 = new Employee_Fix();
		x4.a = 10;
		x4.b = 10;

		Map<Employee_Fix, Employee_Fix> sample = new TreeMap<>(
				Employee_Fix.Comparators.sortAscendingByAFieldWithDupeEntries);
		sample.put(x, x);
		sample.put(x1, x1);
		sample.put(x2, x2);
		sample.put(x3, x3);
		sample.put(x4, x4);

		System.out.println(sample.size());
		sample.forEach((m, n) -> {
			System.out.println(m.a + " " + n.b);
		});

	}

	public static void test6() {

		Employee_Fix x = new Employee_Fix();
		x.a = 10;
		x.b = 20;

		Employee_Fix x1 = new Employee_Fix();
		x1.a = 10;
		x1.b = 20;

		Employee_Fix x2 = new Employee_Fix();
		x2.a = 30;
		x2.b = 40;

		Employee_Fix x3 = new Employee_Fix();
		x3.a = 5;
		x3.b = 5;

		Employee_Fix x4 = new Employee_Fix();
		x4.a = 10;
		x4.b = 10;

		Map<Employee_Fix, Employee_Fix> sample = new TreeMap<>(
				Employee_Fix.Comparators.sortAscendingByAFieldWithoutDupeEntries);
		sample.put(x, x);
		sample.put(x1, x1);
		sample.put(x2, x2);
		sample.put(x3, x3);
		sample.put(x4, x4);

		System.out.println(sample.size());
		sample.forEach((m, n) -> {
			System.out.println(m.a + " " + n.b);
		});

	}

	public static void test7() {

		Employee_Fix x = new Employee_Fix();
		x.a = 10;
		x.b = 20;

		Employee_Fix x1 = new Employee_Fix();
		x1.a = 10;
		x1.b = 20;

		Employee_Fix x2 = new Employee_Fix();
		x2.a = 30;
		x2.b = 10;

		Employee_Fix x3 = new Employee_Fix();
		x3.a = 5;
		x3.b = 45;

		Employee_Fix x4 = new Employee_Fix();
		x4.a = 10;
		x4.b = 30;

		Map<Employee_Fix, Employee_Fix> sample = new TreeMap<>(
				Employee_Fix.Comparators.sortAscendingByBFieldWithDupeEntries);
		sample.put(x, x);
		sample.put(x1, x1);
		sample.put(x2, x2);
		sample.put(x3, x3);
		sample.put(x4, x4);

		System.out.println(sample.size());
		sample.forEach((m, n) -> {
			System.out.println(m.a + " " + n.b);
		});

	}

	public static void test8() {

		Employee_Fix x = new Employee_Fix();
		x.a = 10;
		x.b = 60;

		Employee_Fix x1 = new Employee_Fix();
		x1.a = 10;
		x1.b = 60;

		Employee_Fix x2 = new Employee_Fix();
		x2.a = 30;
		x2.b = 40;

		Employee_Fix x3 = new Employee_Fix();
		x3.a = 5;
		x3.b = 55;

		Employee_Fix x4 = new Employee_Fix();
		x4.a = 10;
		x4.b = 50;

		Map<Employee_Fix, Employee_Fix> sample = new TreeMap<>(
				Employee_Fix.Comparators.sortAscendingByBFieldWithoutDupeEntries);
		sample.put(x, x);
		sample.put(x1, x1);
		sample.put(x2, x2);
		sample.put(x3, x3);
		sample.put(x4, x4);

		System.out.println(sample.size());
		sample.forEach((m, n) -> {
			System.out.println(m.a + " " + n.b);
		});

	}

	public static void test9() {

		Employee_Fix x = new Employee_Fix();
		x.a = 10;
		x.b = 20;

		Employee_Fix x1 = new Employee_Fix();
		x1.a = 10;
		x1.b = 20;

		Employee_Fix x2 = new Employee_Fix();
		x2.a = 30;
		x2.b = 40;

		Employee_Fix x3 = new Employee_Fix();
		x3.a = 5;
		x3.b = 5;

		Employee_Fix x4 = new Employee_Fix();
		x4.a = 10;
		x4.b = 10;

		Map<Employee_Fix, Employee_Fix> sample = new TreeMap<>(
				Employee_Fix.Comparators.sortAscendingByAAndBFieldWithDupeEntries);
		sample.put(x, x);
		sample.put(x1, x1);
		sample.put(x2, x2);
		sample.put(x3, x3);
		sample.put(x4, x4);

		System.out.println(sample.size());
		sample.forEach((m, n) -> {
			System.out.println(m.a + " " + n.b);
		});

	}

	public static void test10() {

		Employee_Fix x = new Employee_Fix();
		x.a = 10;
		x.b = 20;

		Employee_Fix x1 = new Employee_Fix();
		x1.a = 10;
		x1.b = 20;

		Employee_Fix x2 = new Employee_Fix();
		x2.a = 30;
		x2.b = 40;

		Employee_Fix x3 = new Employee_Fix();
		x3.a = 5;
		x3.b = 5;

		Employee_Fix x4 = new Employee_Fix();
		x4.a = 10;
		x4.b = 10;

		Map<Employee_Fix, Employee_Fix> sample = new TreeMap<>(
				Employee_Fix.Comparators.sortAscendingByAAndBFieldWithoutDupeEntries);
		sample.put(x, x);
		sample.put(x1, x1);
		sample.put(x2, x2);
		sample.put(x3, x3);
		sample.put(x4, x4);

		System.out.println(sample.size());
		sample.forEach((m, n) -> {
			System.out.println(m.a + " " + n.b);
		});

	}

	public static void test11() {

		Employee_Fix_1 x = new Employee_Fix_1();
		x.a = 10;
		x.b = 20;

		Employee_Fix_1 x1 = new Employee_Fix_1();
		x1.a = 10;
		x1.b = 20;

		Employee_Fix_1 x2 = new Employee_Fix_1();
		x2.a = 30;
		x2.b = 40;

		Employee_Fix_1 x3 = new Employee_Fix_1();
		x3.a = 5;
		x3.b = 5;

		Employee_Fix_1 x4 = new Employee_Fix_1();
		x4.a = 10;
		x4.b = 10;

		Map<Employee_Fix_1, Employee_Fix_1> sample = new TreeMap<>(
				Employee_Fix_1.Comparators.sortAscendingByAFieldWithDupeEntries);
		sample.put(x, x);
		sample.put(x1, x1);
		sample.put(x2, x2);
		sample.put(x3, x3);
		sample.put(x4, x4);

		System.out.println(sample.size());
		sample.forEach((m, n) -> {
			System.out.println(m.a + " " + n.b);
		});

	}

}

// ClassCastException will be thrown if added into TreeSet or TreeMap Collection
// classes.
class Employee {
	int a;
	int b;

}

// Implementing Comparable is mandatory for the instances to be added into
// Collection
// classes such as TreeSet, TreeMap and the likes, if no comparator is used.
class Employee_Fix implements Comparable<Employee_Fix> {
	int a;
	int b;

	@Override
	public int compareTo(Employee_Fix o) {
		// TODO Auto-generated method stub

		if (this.equals(o)) {
			// This will not allow duplicate elements to be added into the collection.
			// If duplicate elements need to be allowed then never return 0 value from
			// compareTo method or the comparator based compare method or the lambda
			// expression.
			return 0;
		} else {
			// Not trying to sort by anything, but just want to allow the addition of
			// elements into the collection classes like TreeSet, TreeMap.
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

	static class Comparators {

		static Comparator<Employee_Fix> sortAscendingByAFieldWithDupeEntries = (e1, e2) -> {
			if (e1.a <= e2.a) {
				return -1;
			} else {
				return 1;
			}
		};

		// wrote for testing purpose.
		static Comparator<Employee_Fix> sortAscendingByAFieldWithoutDupeEntries = (e1, e2) -> {
			if (e1.equals(e2)) {// duplicate map entries are not added.
				return 0;
			} else {
				if (e1.a == e2.a) {// map entries with same field 'a' values are not added.
					return 0;
				} else {
					return sortAscendingByAFieldWithDupeEntries.compare(e1, e2);
				}
			}
		};

		static Comparator<Employee_Fix> sortAscendingByBFieldWithDupeEntries = (e1, e2) -> {
			if (e1.b <= e2.b) {
				return -1;
			} else {
				return 1;
			}
		};

		// wrote for testing purpose.
		static Comparator<Employee_Fix> sortAscendingByBFieldWithoutDupeEntries = (e1, e2) -> {
			if (e1.equals(e2)) {// duplicate map entries are not added.
				return 0;
			} else {
				if (e1.b == e2.b) {// map entries with same field 'b' values are not added.
					return 0;
				} else {
					return sortAscendingByBFieldWithDupeEntries.compare(e1, e2);
				}
			}
		};

		static Comparator<Employee_Fix> sortAscendingByAAndBFieldWithDupeEntries = (e1, e2) -> {
			if (e1.a == e2.a) {
				return sortAscendingByBFieldWithDupeEntries.compare(e1, e2);
			} else {
				return sortAscendingByAFieldWithDupeEntries.compare(e1, e2);
			}
		};

		private static Comparator<Employee_Fix> compareFieldA = (e1, e2) -> {
			//// Approach-1:
			if (e1.a == e2.a) {
				return 0;
			} else if (e1.a < e2.a) {
				return -1;
			} else {
				return 1;
			}

			// Approach-2:
			// return e1.a - e2.a;

			// Approach-3:
			// return Integer.valueOf(e1.a).compareTo(Integer.valueOf(e2.a));
		};

		private static Comparator<Employee_Fix> compareFieldB = (e1, e2) -> {
			return Integer.valueOf(e1.b).compareTo(Integer.valueOf(e2.b));
		};

		static Comparator<Employee_Fix> sortAscendingByAAndBFieldWithoutDupeEntries = (e1, e2) -> {
			if (e1.equals(e2)) {
				return 0;// This will disallow duplicate elements from entering the collection.
			} else {

				int val = compareFieldA.compare(e1, e2);

				if (val == 0) {
					return compareFieldB.compare(e1, e2);
					// for any more fields comparison, the logic needs to be built here.
				} else {
					return val;
				}

			}
		};

	}

}

// Testing Class without compareTo implementation, but with comparator for
// adding objects in TreeMap.
class Employee_Fix_1 {
	int a;
	int b;

	static class Comparators {

		static Comparator<Employee_Fix_1> sortAscendingByAFieldWithDupeEntries = (e1, e2) -> {
			if (e1.a <= e2.a) {
				return -1;
			} else {
				return 1;
			}
		};
	}

}
