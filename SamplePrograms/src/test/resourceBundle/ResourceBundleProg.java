package test.resourceBundle;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class ResourceBundleProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Museum.currentStatus();
		
		ProgrammingPointOfView.PropertyFileStore.showPropertyFileContents();
		ProgrammingPointOfView.PropertyFileStore.createPropertiesObject().test();
		ProgrammingPointOfView.JavaFileStore.createPropertiesObject().test();
	}
	
	static class Museum{
		
		static void displayStatus(Locale locale) {
			ResourceBundle rb = ResourceBundle.getBundle("test.resourceBundle.Museum", locale);
			System.out.println(rb.getString("hello"));
			System.out.println(rb.getString("open"));
		}
		
		static void currentStatus() {
			Locale inEnglish = new Locale("en", "IN");
			Locale inFrench = new Locale("fr", "FR");
			
			System.out.println("Museum Status in English:");
			Museum.displayStatus(inEnglish);
			
			System.out.println("Museum Status in French:");
			Museum.displayStatus(inFrench);
		}
	}
	
	static class ProgrammingPointOfView{
	
		static class PropertyFileStore{
			
			static void showPropertyFileContents() {
				Locale india = new Locale("en", "IN");
				Locale france = new Locale("fr", "FR");
				
				usingEnumeration();//hard-coded locale
				usingSetIterator(france);//dynamic
				usingLambdaExpression(india);
			}
			
			//order is not guaranteed.
			private static void usingEnumeration() {
				System.out.println("Display Contents using Enumeration:");
				ResourceBundle rb = ResourceBundle.getBundle("test.resourceBundle.Museum_en");
				Enumeration <String> keys = rb.getKeys();
				String key, value;
				while (keys.hasMoreElements()) {
					key = keys.nextElement();
					value = rb.getString(key);
					System.out.println(key + ": " + value);
				}
			}
			
			//order is guaranteed.
			private static void usingSetIterator(Locale locale) {
				System.out.println("Display Contents using Set and the Iterator:");
				ResourceBundle rb = ResourceBundle.getBundle("test.resourceBundle.Museum", locale);
				Set<String> keys = rb.keySet();
				Iterator<String> setIterator = keys.iterator();
				String key, value;
				while(setIterator.hasNext()) {
					key = setIterator.next();
					value = rb.getString(key);
					System.out.println(key + ": " + value);
				}			
			}
			
			//order is guaranteed.
			private static void usingLambdaExpression(Locale locale) {
				System.out.println("Display Contents using Streams and Lambda Expression:");
				ResourceBundle rb = ResourceBundle.getBundle("test.resourceBundle.Museum", locale);
				Set<String> keys = rb.keySet();
				keys.stream().map(k -> k + " " + rb.getString(k))
				.forEach(System.out::println);
			}
			
			static GenericTest createPropertiesObject() {
				ResourceBundle rb = ResourceBundle.getBundle("test.resourceBundle.Museum_en");
				Properties props = new Properties();
				rb.keySet().stream()
				.forEach(k -> props.put(k, rb.getString(k)));
								
				class CreationTest implements GenericTest{
					public void test(){	
						System.out.println("Testing created Properties Object:");
						System.out.println(props.getProperty("keyNotPresentTest"));
						System.out.println(props.getProperty("keyNotPresentTest", "UseThisValueIfKeyNotPresent"));
						System.out.print("This info is pulled from the properties file:");
						System.out.println(props.getProperty("open"));					
					}
				}
				
				return new CreationTest();
			}
		}
		
		static class JavaFileStore{
			
			static GenericTest createPropertiesObject() {
				
				//cannot have the class - test.resourceBundle.MuseumJava_en_US
				//as part of this class as an inner class due to issues with 
				//'baseName' ('resourcePath' variable value).
				
				class CreationTest implements GenericTest{
					
					private void doTest(String resourcePath, Locale locale) {
						
						ResourceBundle rb = ResourceBundle.getBundle(
								resourcePath, locale);
						
						System.out.println(rb.getObject("someClass"));
						System.out.print(rb.getObject("hello")+" ");
						System.out.println(rb.getObject("open"));
					}
					
					public void test(){
						
						//We want the resource-bundle - 'MuseumJava_en_US' with 
						//'Locale.US' locale. Thus, below resource path is dynamic
						// and not bound to any specific locale file path such as below.
						//String resourcePath = "test.resourceBundle.MuseumJava_en_US";
						
						String resourcePath = "test.resourceBundle.MuseumJava";						
												
						doTest(resourcePath, Locale.US);
						doTest(resourcePath, Locale.FRANCE);
						
					}
				}
				
				return new CreationTest();
			}
						
		}		
		
	}
	
	interface GenericTest{
		 void test();
	}
}

/*
 * Note-1: ResourceBundle.getBundle will look for the following entities:
 * 		Format: UserDefinedName_{LanguageCode}_{CountryCode}.{java|properties} 
 * 				where {} is an optional entity.		
 * 
 * 		Example: UserDefinedName is 'Museum', LanguageCode is 'fr', 
 * 				 and CountryCode is 'FR'.
 * 		1. Museum_fr_FR.java
 * 		2. Museum_fr_FR.properties
 * 		3. Museum_fr.java
 * 		4. Museum_fr.properties
 * 		5. Museum.java
 * 		6. Museum.properties
 * 		7. If not found, throws MissingResourceException.
 */
