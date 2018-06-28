package com.example.SpringRestSample1.exceptions;

/**
 * This class can have any other variable other than the 
 * below one, but the Java Rest client has access to the
 * server error issue only via the below variable. 
 * 
 * public String message
 * 
 * Test Method: SampleClient.test4()
 * 
 * @author Sawan.Patwari
 *
 */
public class Error1 {
	//This will not be used on the Java Spring Rest client.
	public String message1;
	
	
	//changing the variable name to something else will result in not 
	//using this value at the Java Spring Rest client.
	public String message;
	
	//This will not be used on the Java Spring Rest client.
	//public String standardMessage = "This is an error from Server";
}
