package defaultPackage;

import java.text.NumberFormat;
import java.util.Locale;

public class TestQuickProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Locale.setDefault(Locale.GERMANY);
		double x = 123456.789;
		System.out.println(x);
		
		System.out.println(NumberFormat.getInstance().format(x));
		
	}

}
