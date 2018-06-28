package com.example.SpringRestSample1.exceptions;

import org.springframework.web.client.RestClientException;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class SampleException extends RestClientException{

	public SampleException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public SampleException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SampleException() {
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
