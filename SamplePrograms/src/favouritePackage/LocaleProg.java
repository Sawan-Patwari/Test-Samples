package favouritePackage;

import java.text.NumberFormat;
import java.util.Locale;


/**
 * 
 * @author Sawan.Patwari
 *
 */
public class LocaleProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		doFormating();
	}
		
	static void doFormating() {//likewise date-formating can also be done.
	
		@SuppressWarnings("unused")
		Object numberFormatingType1 = new Object() {
			
			double number = 1_23_456.789;//same as 1,23,456.789
			
			void display(String asPerWhichCountry, Locale locale){
				
				System.out.print(asPerWhichCountry);
				System.out.println(NumberFormat.getInstance(locale).format(number));
			}
			
			{
				System.out.println("Number Formating Samples for the number:"+number);
				
				display("As per your country:", Locale.getDefault());
				display("As per Canada:", Locale.CANADA);
				display("As per Canada-French:", Locale.CANADA_FRENCH);
				display("As per Germany:", Locale.GERMANY);
				display("As per Swiss German:", new Locale("gsw","CH"));
				display("As per US:", Locale.US);								
				display("As per UK:", Locale.UK);
				
			}
		};
	
		@SuppressWarnings("unused")
		Object currencyFormatingType1 = new Object() {
			double amount = 1_23_456.789;
			
			void display(String asPerWhichCountry, Locale locale){
				
				System.out.print(asPerWhichCountry);
				System.out.println(NumberFormat.getCurrencyInstance(locale).format(amount));
			}
			
			{
				System.out.println("Currency Formating Samples for the amount:"+amount);
				
				display("As per your country:", Locale.getDefault());
				display("As per Canada:", Locale.CANADA);
				display("As per Canada-French:", Locale.CANADA_FRENCH);
				display("As per Germany:", Locale.GERMANY);
				display("As per Swiss German:", new Locale("gsw","CH"));
				display("As per US:", Locale.US);								
				display("As per UK:", Locale.UK);
			
			}
		};
		
		@SuppressWarnings("unused")
		Object numberFormatingType2 = new Object() {
			
			class FormatNumber implements Display {
				
				public void doDisplay() {
					System.out.println("Number Formating Samples for the number:"+figure);
					doGenericDisplay();
				}
				
				public String doFormat(Locale locale) {
					if(locale == null) locale = Locale.getDefault();
					
					return NumberFormat.getInstance(locale).format(figure);
				}
			}

			{
				new FormatNumber().doDisplay();											
			}
		};
		
		@SuppressWarnings("unused")
		Object currencyFormatingType2 = new Object() {
						
			class FormatCurrency implements Display {				
				
				public void doDisplay() {
					System.out.println("Currency Formating Samples for the amount:"+figure);	
					doGenericDisplay();
				}
				
				public String doFormat(Locale locale) {
					if(locale == null) locale = Locale.getDefault();
					
					return NumberFormat.getCurrencyInstance(locale).format(figure);
				}
				
			}

			{							
				new FormatCurrency().doDisplay();				
			}
		};

	}

}

interface Display extends Format{
	default void display(String asPerWhichCountry, Locale locale) {
		
		System.out.print(asPerWhichCountry);
		System.out.println(doFormat(locale));
	}

	default void doGenericDisplay() {//this is what is intended for display as part of this program.
		display("As per your country:", Locale.getDefault());
		//Samples from other countries.
		display("As per Canada:", Locale.CANADA);
		display("As per Canada-French:", Locale.CANADA_FRENCH);
		display("As per Germany:", Locale.GERMANY);
		display("As per Swiss German:", new Locale("gsw", "CH"));
		display("As per US:", Locale.US);
		display("As per UK:", Locale.UK);
	}
	
	void doDisplay();
}

interface Format {
	double figure = 9_46_789.789;//Using hard-coded value for sample purpose.
	String doFormat(Locale locale);	
}
