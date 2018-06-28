package com.example.SpringRestSample1.exceptions;

/**
 * This class cannot have any other variable other than the 
 * below one, if the Java Rest client has to have access to the
 * server error issue. 
 * 
 * public String message
 * 
 * Test Method: SampleClient.test3() 
 * 
 * @author Sawan.Patwari
 *
 */
public class Error {
	//This will not be used on the Java Spring Rest client.
	//public String message1;
	
	
	//changing the variable name to something else will result in not 
	//using this value at the Java Spring Rest client.
	public String message;
	
	//This will not be used on the Java Spring Rest client.
	//public String standardMessage = "This is an error from Server";
}
