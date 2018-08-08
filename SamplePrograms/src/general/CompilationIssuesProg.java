package general;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class CompilationIssuesProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class Test1{
		static class Parent {
			public static void test() {
				System.out.println("Calling from Parent: test()");
			}
			
			public void test1() {
				System.out.println("Calling from Parent: test1()");
			}
		}
		
		class Child extends Parent {
			
			public void test() {
				System.out.println("Calling from Child: test()");
			}
			
			public static void test1() {
				System.out.println("Calling from Child: test1()");
			}
			
		}
}