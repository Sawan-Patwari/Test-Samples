package test.locale;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
		doParse();
		doDateTimeFormat();
		doDateAndTimeParse();
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
	
	static void doParse() {
		
		String one = "75987abc";
		String two = "-5.26474x10";
		String three = "x85.3";
		String four = "75987abc34";
		String amt = "11,363.99$";
		String amt1 = "$11,363.99";
		
		NumberFormat nf = NumberFormat.getInstance();
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		
		try {
			System.out.println(nf.parse(one));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			System.out.println(nf.parse(two));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(nf.parse(three));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(nf.parse(four));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(nf.parse(amt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		try {
			double value = (Double) cf.parse(amt);
			System.out.println(value); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			double value = (Double) cf.parse(amt1);
			System.out.println(value); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void doDateTimeFormat() {
		
		LocalDate date = LocalDate.of(2012, Month.JANUARY, 24);
		LocalTime time = LocalTime.of(11, 12, 34);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
		System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		//DateTimeFormatter - ofLocalizedDate, ofLocalizedTime, OfLocalizedDateTime.
		//FormatStyle.SHORT, FormatStyle.MEDIUM
		
		DateTimeFormatter shortDate =
				DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		try {
			System.out.println(shortDate.format(dateTime));
			
			System.out.println(shortDate.format(date));
		
			System.out.println(shortDate.format(time)); // UnsupportedTemporalTypeException
		}catch(Exception e) {
			System.out.println(e);
		}
		
		DateTimeFormatter shortDateTimeF = DateTimeFormatter
				.ofLocalizedDateTime(FormatStyle.SHORT);
		
		DateTimeFormatter mediumDateTimeF = DateTimeFormatter
				.ofLocalizedDateTime(FormatStyle.MEDIUM);
				
		System.out.println(shortDateTimeF.format(dateTime));				
		System.out.println(mediumDateTimeF.format(dateTime));
		
		DateTimeFormatter shortTimeF = DateTimeFormatter
				.ofLocalizedTime(FormatStyle.SHORT);
		System.out.println(shortTimeF.format(time));
		
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm");
		System.out.println(dateTime.format(f));
	}
	
	//Generally, we parse to check if the format of the string is correct 
	//(as per the defined format or the default format) and 
	//then get the Object (example: Date and Time based object) instances 
	//from the string else throw an exception.
	static void doDateAndTimeParse() {
				
		try {
			DateTimeFormatter parsingFormat = DateTimeFormatter.ofPattern("MM dd yyyy");
			LocalDate date = LocalDate.parse("08 21 2009", parsingFormat);
			System.out.println(date);
						
			System.out.println(LocalDate.parse("05 03-2015", parsingFormat));
		}catch(Exception e) {
			System.out.println(e);
		}
		
		LocalTime time;
		try {
			time = LocalTime.parse("9:10");			
			System.out.println(time);		
		}catch(Exception e) {
			System.out.println(e);
		}
		
		time = LocalTime.parse("09:10");			
		System.out.println(time);
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
