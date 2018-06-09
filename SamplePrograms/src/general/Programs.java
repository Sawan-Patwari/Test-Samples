package general;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class Programs {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ToStringSample._do();
		EqualsBuilderSample._do();
	}

}

class ToStringSample {
	public String uniqueField;
	public String fieldOne;
	public String fieldTwo;
	public String fieldThree;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String toStringWithJSONStyle() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
	
	public String toStringWithShortPrefixStyle() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public static void _do() {
		
		ToStringSample obj = new ToStringSample();
		obj.uniqueField = "0";
		obj.fieldOne = "1";
		obj.fieldTwo = "2";
		obj.fieldThree = "3";
		
		System.out.println(obj);
		
		System.out.println(obj.toStringWithJSONStyle());
		
		System.out.println(obj.toStringWithShortPrefixStyle());
	}
}

class EqualsBuilderSample {
	public String uniqueField;
	public String fieldOne;
	public String fieldTwo;
	public String fieldThree;
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public boolean equalsWithExcludes(Object obj) {
		// TODO Auto-generated method stub
		return EqualsBuilder.reflectionEquals(this, obj, "uniqueField");
	}
	
	public static void _do() {
		
		EqualsBuilderSample sample1 = new EqualsBuilderSample();
		sample1.uniqueField = "0";
		sample1.fieldOne = "1";
		sample1.fieldTwo = "2";
		sample1.fieldThree = "3";
		
		EqualsBuilderSample sample2 = new EqualsBuilderSample();
		sample2.uniqueField = "0";
		sample2.fieldOne = "1";
		sample2.fieldTwo = "2";
		sample2.fieldThree = "3";
		
		EqualsBuilderSample sample3 = new EqualsBuilderSample();
		sample3.uniqueField = "-1";
		sample3.fieldOne = "1";
		sample3.fieldTwo = "2";
		sample3.fieldThree = "3";
		
		System.out.println(sample1.equals(sample2));
		System.out.println(sample1.equals(sample3));
		System.out.println(sample1.equalsWithExcludes(sample3));
	}
	
}

