package general;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class HashCodeProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Base1Level1a.test1();
		Base1Level1a.test2();
		Base1Level1a.test3();
		Base1Level1a.test4();
		Employee.test();
	}

}

@SuppressWarnings("unused")
class Base1 {

	private int a;
	private int b;
	private int c;

	@HashCodeExclude 
	private int d;
	
	public Base1() {
		
	}
	
	public Base1(int v1, int v2, int v3, int v4) {
		a = v1;
		b = v2;
		c = v3;
		d = v4;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub

		return HashCodeBuilder.reflectionHashCode(this, "c");
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		
		Base1 otherObject = (Base1) obj;
		if (this.hashCode() == otherObject.hashCode()) {
			return EqualsBuilder.reflectionEquals(this, obj, "c", "d");
		} else {
			return false;
		}
	}
}

@SuppressWarnings("unused")
class Base1Level1a extends Base1 {

	private int e;
	private int f;
	private int g;

	public Base1Level1a() {

	}

	public Base1Level1a(int v1, int v2, int v3, int v4, int v5, int v6) {
		
		super(v1, v4, v5, v6);
		
		e = v1;
		f = v2;
		g = v3;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		
		//Excluding two fields(c, d) while computing the hash-code.
		//Field 'd' is excluded using the annotations - @HashCodeExclude. 
		return HashCodeBuilder.reflectionHashCode(this, "c");
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub

		Base1Level1a otherObject = (Base1Level1a) obj;
		if (this.hashCode() == otherObject.hashCode()) {
			
			//excluding those fields (c, d) which are not part of hashcode() computation.
			return EqualsBuilder.reflectionEquals(this, obj, "c", "d");
		} else {
			return false;
		}
	}

	public void doHashCodeTest() {

		int previousValue = hashCode();
		int currentValue = 0;

		System.out.println("Hash code: " + previousValue);

		for (int i = 0; i < 10; i++) {
			currentValue = hashCode();
			if (previousValue != currentValue) {
				System.out.println("New Hash code: " + currentValue);
			}
		}

	}

	public static void test1() {
		System.out.println("Test1: [Started]");
		Base1Level1a test1 = new Base1Level1a(1, 2, 3, 4, 5, 6);
		test1.doHashCodeTest();
		
		Base1Level1a test1_1 = new Base1Level1a(2, 3, 1, 4, 5, 6);
		test1_1.doHashCodeTest();

		Base1Level1a test2 = new Base1Level1a(4, 5, 6, 4, 5, 6);
		test2.doHashCodeTest();
		
		Base1Level1a test2_1 = new Base1Level1a(5, 6, 4, 4, 5, 6);
		test2_1.doHashCodeTest();
		System.out.println("Test1: [Ended]");
	}

	public static void test2() {
		System.out.println("Test2: [Started]");
		Base1Level1a test1 = new Base1Level1a();
		test1.doHashCodeTest();

		Base1Level1a test2 = new Base1Level1a();
		test2.doHashCodeTest();
		System.out.println("Test2: [Ended]");
	}
	
	public static void test3() {
		System.out.println("Test3 [Started]");
		
		Base1Level1a object1 = new Base1Level1a(1, 2, 3, 4, 5, 6);
		System.out.println("Base1Level1(1, 2, 3, 4, 5, 6): "+object1.hashCode());
		Base1Level1a object2 = new Base1Level1a(1, 2, 3, 4, 7, 8);
		System.out.println("Base1Level1(1, 2, 3, 4, 7, 8): "+object2.hashCode());
		
		if(object1.equals(object2)) {
			System.out.println("Both objects are equal.");
		} else {
			System.out.println("Both objects are not equal.");
		}
				
		System.out.println("Test3: [Ended]");
	}
	
	public static void test4() {
		System.out.println("test4 [Started]");
		
		Base1Level1a object1 = new Base1Level1a(1, 2, 3, -1, 5, 6);
		System.out.println("Base1Level1(1, 2, 3, -1, 5, 6): "+object1.hashCode());
		Base1Level1a object2 = new Base1Level1a(1, 2, 3, 4, 7, 8);
		System.out.println("Base1Level1(1, 2, 3, 4, 7, 8): "+object2.hashCode());
		
		if(object1.equals(object2)) {
			System.out.println("Both objects are equal.");
		} else {
			System.out.println("Both objects are not equal.");
		}
				
		System.out.println("test4: [Ended]");
	}
}

class Employee {
	int a;
	int b;
	
	/*
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	} */
	
	static void test() {
		Employee x = new Employee();
		x.a = 10;
		x.b = 20;
		
		Employee x1 = new Employee();
		x1.a = 10;
		x1.b = 20;
		
		Map<Integer, Employee> map = new HashMap<>();
		
		map.put(1, x);
		map.put(1, x1);
		System.out.println(map.size());
		
		System.out.println("Hash Codes of x:"+x.hashCode());
		System.out.println("Hash Codes of x1:"+x1.hashCode());
		
		Employee z = x;
		System.out.println("Hash Codes of z:"+z.hashCode());
		
	}
}

