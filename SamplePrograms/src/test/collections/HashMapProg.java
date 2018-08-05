package test.collections;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class HashMapProg {

	private static HashMapProg sample = new HashMapProg();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test2();
	}

	public static void test1() {
		Employee x = sample.new Employee();
		x.a = 10;
		x.b = 20;

		Employee x1 = sample.new Employee();
		x1.a = 10;
		x1.b = 20;

		Map<Integer, Employee> map = new HashMap<>();

		map.put(1, x);
		map.put(1, x1);
		System.out.println(map.size());

	}

	public static void test1_1() {
		Employee x = sample.new Employee();
		x.a = 10;
		x.b = 20;

		Employee x1 = sample.new Employee();
		x1.a = 10;
		x1.b = 20;

		System.out.println("Hash Codes of x:" + x.hashCode());
		System.out.println("Hash Codes of x1:" + x1.hashCode());

		Employee z = x;
		System.out.println("Hash Codes of z:" + z.hashCode());
	}

	public static void test2() {
		Employee x = sample.new Employee();
		x.a = 10;
		x.b = 20;

		Employee x1 = sample.new Employee();
		x1.a = 10;
		x1.b = 20;
		
		Employee x2 = sample.new Employee();
		x2.a = 10;
		x2.b = 20;

		Map<Employee, Employee> map = new HashMap<>();
		map.put(x, x);
		map.put(x1, x1);
		map.put(x1, x1);
		map.put(x2, x2);
		System.out.println(map.size());

	}

	public static void test3() {
		Employee1 x = sample.new Employee1();
		x.a = 10;
		x.b = 20;

		Employee1 x1 = sample.new Employee1();
		x1.a = 10;
		x1.b = 20;

		if (x.equals(x1)) {
			System.out.println("Objects equal");
		} else {
			System.out.println("Objects not equal");
		}

		Map<Employee1, Employee1> map = new HashMap<>();
		map.put(x, x);
		map.put(x1, x1);
		System.out.println(map.size());

	}

	public static void test4() {
		Employee2 x = sample.new Employee2();
		x.a = 10;
		x.b = 20;

		Employee2 x1 = sample.new Employee2();
		x1.a = 10;
		x1.b = 20;

		if (x.equals(x1)) {
			System.out.println("Objects equal");
		} else {
			System.out.println("Objects not equal");
		}

		System.out.println("Hashcode of x:" + x.hashCode());
		System.out.println("Hashcode of x1:" + x1.hashCode());

		Map<Employee2, Employee2> map = new HashMap<>();
		map.put(x, x);
		map.put(x1, x1);
		System.out.println(map.size());

	}

	public static void test5() {
		Employee3 x = sample.new Employee3();
		x.a = 10;
		x.b = 20;

		Employee3 x1 = sample.new Employee3();
		x1.a = 10;
		x1.b = 20;

		if (x.equals(x1)) {
			System.out.println("Objects equal");
		} else {
			System.out.println("Objects not equal");
		}

		Map<Employee3, Employee3> map = new HashMap<>();
		map.put(x, x);
		map.put(x1, x1);
		System.out.println(map.size());

	}
	
	public static void test6() {
		Employee4 x = new Employee4();
		x.a = 10;
		x.b = 20;

		Employee4 x1 = new Employee4();
		x1.a = 10;
		x1.b = 20;
		
		Employee4 x2 = new Employee4();
		x2.a = 05;
		x2.b = 35;

		Employee4 x3 = new Employee4();
		x3.a = 95;
		x3.b = 05;
		
		Employee4 x4 = new Employee4();
		x4.a = 75;
		x4.b = 10;
		
		//Cannot sort the HashMap on its own.
		Map<Employee4, Employee4> map = new HashMap<>();
		map.put(x, x);
		map.put(x1, x1);
		map.put(x2, x2);
		map.put(x3, x3);
		map.put(x4, x4);
		
		Map<Employee4, Employee4> sortMap = new TreeMap<>(Employee4.Comparators.sortAscendingByAFieldWithDupeEntries);
		sortMap.putAll(map);
		System.out.println("Size:"+map.size());
		sortMap.forEach((m, n) -> {
			System.out.println(m.a + " " +n.b);
		});
		
	}

	class Employee {
		int a;
		int b;

		/*
		 * @Override public int hashCode() { return
		 * HashCodeBuilder.reflectionHashCode(this); }
		 */
	}

	class Employee1 {
		int a;
		int b;

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return EqualsBuilder.reflectionEquals(this, (Employee1) obj);
		}

		/*
		 * @Override public int hashCode() { return
		 * HashCodeBuilder.reflectionHashCode(this); }
		 */
	}

	class Employee2 {
		int a;
		int b;

		/*
		 * @Override public boolean equals(Object obj) { // TODO Auto-generated method
		 * stub return EqualsBuilder.reflectionEquals(this, (Employee2)obj); }
		 */

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}
	}

	class Employee3 {
		int a;
		int b;

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub

			// simple one line code:
			// return EqualsBuilder.reflectionEquals(this, (Employee3)obj);

			// better improved code:
			Employee3 other = (Employee3) obj;
			
			if(this == other) {
				return true;
			} else {
			
				if (this.hashCode() == other.hashCode()) {
					return EqualsBuilder.reflectionEquals(this, other);
				} else {
					return false;
				}
			}
			
		}

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

	}

}

class Employee4 {
	int a;
	int b;

	static class Comparators {

		static Comparator<Employee4> sortAscendingByAFieldWithDupeEntries = (e1, e2) -> {
			if (e1.a <= e2.a) {
				return -1;
			} else {
				return 1;
			}
		};
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub

		// simple one line code:
		// return EqualsBuilder.reflectionEquals(this, (Employee3)obj);

		// better improved code:
		Employee4 other = (Employee4) obj;
		
		if(this == other) {
			return true;
		} else {
		
			if (this.hashCode() == other.hashCode()) {
				return EqualsBuilder.reflectionEquals(this, other);
			} else {
				return false;
			}
		}
		
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
