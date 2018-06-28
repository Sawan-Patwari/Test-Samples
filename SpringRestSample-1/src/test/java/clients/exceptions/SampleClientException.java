package clients.exceptions;

import org.springframework.web.client.RestClientException;

/**
 * 
 * @author Sawan.Patwari
 *
 */
public class SampleClientException extends RestClientException{

	public SampleClientException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public SampleClientException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SampleClientException() {
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
