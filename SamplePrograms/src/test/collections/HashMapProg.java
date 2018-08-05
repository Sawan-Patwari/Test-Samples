package test.collections;

import java.util.HashMap;
import java.util.Map;

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
		test5();
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

		Map<Employee, Employee> map = new HashMap<>();
		map.put(x, x);
		map.put(x1, x1);
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
