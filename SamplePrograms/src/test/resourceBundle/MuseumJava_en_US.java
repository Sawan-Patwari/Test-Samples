package test.resourceBundle;

import java.util.ListResourceBundle;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class MuseumJava_en_US extends ListResourceBundle{
	
	class SomeClass{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Resource-bundle: MuseumJava_en_US.java created.";
		}
	}
	
	@Override
	protected Object[][] getContents() {
		
		return new Object[][] {
					{ "hello", "Hello" },
					{ "open", "The Museum is open." },
					{"someClass", new SomeClass()}
				};
	}
}
