package test.resourceBundle;

import java.util.ListResourceBundle;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class MuseumJava_fr_FR extends ListResourceBundle{
	
	class SomeClass{
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Resource-bundle: MuseumJava_fr_FR.java created.";
		}
	}
	
	@Override
	protected Object[][] getContents() {
		
		return new Object[][] {
					{ "hello", "Bonjour!" },
					{ "open", "Le Museum est ouvert." },
					{"someClass", new SomeClass()}
				};
	}
}
