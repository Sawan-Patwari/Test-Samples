package com.example.SpringRestSample1.exceptions;


/**
 * 
 * @author Sawan.Patwari
 *
 */
public class SampleServerException extends RuntimeException{

	public SampleServerException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public SampleServerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SampleServerException() {
		super("A Sample Exception");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exceptionName;

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	
}
