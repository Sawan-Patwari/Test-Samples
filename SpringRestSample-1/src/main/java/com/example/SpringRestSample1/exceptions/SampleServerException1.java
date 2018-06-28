package com.example.SpringRestSample1.exceptions;


/**
 * 
 * @author Sawan.Patwari
 *
 */
public class SampleServerException1 extends RuntimeException{

	public SampleServerException1(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public SampleServerException1(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SampleServerException1() {
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
