package test.locale;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * 
 * @author Sawan.Patwari
 *
 */
public class LocaleProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		System.out.println("+++++++++++++++++++++++Program Started: ++++++++++++++++++++++++++++");
	
		//New code v1.8
		doFormat();
		doDecimalFormat();
		doNumberParse();
		
		Approach1_8v.doDateTimeFormat();
		ApproachBefore1_8v.doDateTimeFormat();//old code.
				
		Approach1_8v.doDateAndTimeParse();	
		ApproachBefore1_8v.doDateAndTimeParseAndFormat();//old code.
		
		System.out.println("+++++++++++++++++++++++Program Ended: ++++++++++++++++++++++++++++");			
	}
		
	static void doFormat() {//likewise date-formating can also be done.
	
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
	
	static void doDecimalFormat() {
		double d = 12345670.123;
		DecimalFormat one = new DecimalFormat("###,###,###.###");
		System.out.println(one.format(d)); 
		DecimalFormat two = new DecimalFormat("000,000,000.00000");
		System.out.println(two.format(d)); 
		DecimalFormat three = new DecimalFormat("$#,###,###.##");
		System.out.println(three.format(d)); 
	}
	
	static void doNumberParse() {
		
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
			System.out.println(e);
		} 
		try {
			System.out.println(nf.parse(two));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		try {
			System.out.println(nf.parse(three));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		try {
			System.out.println(nf.parse(four));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		try {
			System.out.println(nf.parse(amt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}		
		try {
			double value = (Double) cf.parse(amt);
			System.out.println(value); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		try {
			double value = (Double) cf.parse(amt1);
			System.out.println(value); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}
	
	static class Approach1_8v{
		//On Local based Date and Time objects using DateTimeFormatter.
		static void doDateTimeFormat() {
			
			LocalDate date = LocalDate.of(2012, Month.JANUARY, 24);
			LocalTime time = LocalTime.of(11, 12, 34);
			LocalDateTime dateTime = LocalDateTime.of(date, time);
			System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
			System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
			System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			
			//DateTimeFormatter - ofLocalizedDate, ofLocalizedTime, OfLocalizedDateTime.
			//FormatStyle.SHORT, FormatStyle.MEDIUM
			
			//*******************************
			//ofLocalizedDate
			DateTimeFormatter shortDate =
					DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
			try {
				System.out.println(shortDate.format(dateTime));
				
				System.out.println(shortDate.format(date));
			
				System.out.println(shortDate.format(time)); // UnsupportedTemporalTypeException
			}catch(Exception e) {
				System.out.println(e);
			}
			//*******************************
			
			//*******************************
			//ofLocalizedDateTime
			DateTimeFormatter shortDateTimeF = DateTimeFormatter
					.ofLocalizedDateTime(FormatStyle.SHORT);
			
			DateTimeFormatter mediumDateTimeF = DateTimeFormatter
					.ofLocalizedDateTime(FormatStyle.MEDIUM);
					
			System.out.println(shortDateTimeF.format(dateTime));				
			System.out.println(mediumDateTimeF.format(dateTime));
			
			//*******************************
			
			//*******************************
			//ofLocalizedTime
			DateTimeFormatter shortTimeF = DateTimeFormatter
					.ofLocalizedTime(FormatStyle.SHORT);
			System.out.println(shortTimeF.format(time));
			//*******************************
			
			//*******************************
			//ofPattern
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm");
			System.out.println(dateTime.format(formatter));
			//*******************************
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
				time = LocalTime.parse("4:25");			
				System.out.println(time);		
			}catch(Exception e) {
				System.out.println(e);
			}
			
			time = LocalTime.parse("04:25");			
			System.out.println(time);
		}
	}
	
	static class ApproachBefore1_8v{
		//For formatting Date objects (old way).
		static void doDateTimeFormat() {
			
			//***************
			//getDateInstance
			DateFormat shortFormat = DateFormat.getDateInstance(DateFormat.SHORT);
			DateFormat mediumFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
			DateFormat lonfFormat = DateFormat.getDateInstance(DateFormat.LONG);
			DateFormat fullFormat = DateFormat.getDateInstance(DateFormat.FULL);

			Date date = new GregorianCalendar(2012, Calendar.APRIL, 10).getTime();
			System.out.println(shortFormat.format(date));
			System.out.println(mediumFormat.format(date)); 
			System.out.println(lonfFormat.format(date)); 
			System.out.println(fullFormat.format(date));
			
			//***************
			//getDateTimeInstance
			DateFormat dtf = DateFormat.getDateTimeInstance(
					DateFormat.MEDIUM, DateFormat.FULL);
			System.out.println(dtf.format(date));
					
			//Time-zone is not part of locale.
			DateFormat deDateFormatter = DateFormat.getDateTimeInstance(
					DateFormat.MEDIUM, DateFormat.FULL, Locale.GERMANY);
			System.out.println(deDateFormatter.format(date));
			
			//***************
			
			
			//***************
			//getTimeInstance
			DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.FULL);
			System.out.println(timeFormatter.format(new Date().getTime()));
			
			//***************
		}
		
		static void doDateAndTimeParseAndFormat() {// old code - old way of doing things.
			
			String dateString = "06/21/1990";
			Date date = null;
			
			try {//DateFormat approach.
				//With Locale.GERMANY, we will get 'ParseException'. 
				DateFormat formatUS = DateFormat.getDateInstance(
						DateFormat.SHORT, Locale.US);
				date = formatUS.parse(dateString);
				System.out.println(date);
				
				DateFormat formatFrance = DateFormat.getDateInstance(
						DateFormat.FULL, Locale.FRANCE);
						
				System.out.println(formatFrance.format(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {//SimpleDateFormat approach.
				
				String dateTimeVal = "10 12 2005 09:40:49";
				
				SimpleDateFormat formatType1 = new SimpleDateFormat("MM dd yyyy hh:mm:ss");
				SimpleDateFormat formatType2 = new SimpleDateFormat("MMMM yyyy");
				SimpleDateFormat formatType3 = new SimpleDateFormat("hh");
				
				Date date1 = formatType1.parse(dateTimeVal);
				System.out.println(formatType2.format(date1)); 
				System.out.println(formatType3.format(date1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
