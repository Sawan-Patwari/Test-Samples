package test.locale;

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
			long amount = 9_46_789;
			
			void display(String asPerWhichCountry, Locale locale){
				
				System.out.print(asPerWhichCountry);
				System.out.println(NumberFormat.getCurrencyInstance(locale).format(amount));
			}
			
			{
				System.out.println("Currency Formating Samples for the amount:"+amount);
				
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
				static final double number = 1_23_456.789;//same as 1,23,456.789
				
				public void display(String asPerWhichCountry, Locale locale){
					
					System.out.print(asPerWhichCountry);
					System.out.println(NumberFormat.getInstance(locale).format(number));
				}
				public void doDisplay() {
					System.out.println("Number Formating Samples for the number:"+FormatNumber.number);
					doCommonDisplay();
				}
			}

			{
				new FormatNumber().doDisplay();											
			}
		};
		
		@SuppressWarnings("unused")
		Object currencyFormatingType2 = new Object() {
						
			class FormatCurrency implements Display {
				static final double amount = 9_46_789.789;
				
				public void display(String asPerWhichCountry, Locale locale){
					
					System.out.print(asPerWhichCountry);
					System.out.println(NumberFormat.getCurrencyInstance(locale).format(amount));
				}
				
				public void doDisplay() {
					System.out.println("Currency Formating Samples for the amount:"+FormatCurrency.amount);	
					doCommonDisplay();
				}
				
			}

			{							
				new FormatCurrency().doDisplay();				
			}
		};

	}

}

interface Display {
	void display(String asPerWhichCountry, Locale locale);

	default void doCommonDisplay() {
		display("As per Canada:", Locale.CANADA);
		display("As per Canada-French:", Locale.CANADA_FRENCH);
		display("As per Germany:", Locale.GERMANY);
		display("As per Swiss German:", new Locale("gsw", "CH"));
		display("As per US:", Locale.US);
		display("As per UK:", Locale.UK);
	}
	
	void doDisplay();
}
