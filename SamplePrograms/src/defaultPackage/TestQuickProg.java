package defaultPackage;


/**
 * 
 * @author Sawan.Patwari
 *
 */
public class TestQuickProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//will be deleting the code after the code test.
		
		System.out.println(Integer.class.getTypeName());
		test(Integer.class);
	}
	
	static void test(java.lang.reflect.Type responseType) {
		System.out.println(responseType);
	}

}
