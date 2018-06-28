package com.example.SpringRestSample1.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author Sawan.Patwari
 *
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	//Test Method: SampleClient.test3()
	@ExceptionHandler({ SampleServerException.class })
	public ResponseEntity<? extends Object> handleMyCustomBadRequest(Exception ex, WebRequest request) {
		
		/*
		 * 
		 * 
		 * Issue similar to https://github.com/spring-projects/spring-boot/issues/10846
		 * 
		 * Issue (as seen in the log):
		 * .m.m.a.ExceptionHandlerExceptionResolver : Failed to invoke @ExceptionHandler
		 * method: public org.springframework.http.ResponseEntity<?>
		 * com.example.SpringRestSample1.exceptions.RestExceptionHandler.
		 * handleBadRequest(java.lang.Exception,org.springframework.web.context.request.
		 * WebRequest)
		 * 
		 * org.springframework.http.converter.HttpMessageConversionException: Type
		 * definition error: [simple type, class java.lang.Throwable]; nested exception
		 * is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Direct
		 * self-reference leading to cycle (through reference chain:
		 * com.example.SpringRestSample1.exceptions.SampleException["mostSpecificCause"]
		 * ) at
		 * org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.
		 * writeInternal(AbstractJackson2HttpMessageConverter.java:293)
		 * ~[spring-web-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
		 */
		/*
		 * 
		ResponseEntity.BodyBuilder x = ResponseEntity.status(HttpStatus.BAD_REQUEST);
		// x.body((SampleException)ex);
		SampleException m = new SampleException();
		return x.body(m);
		*/
		
		//Fix for the above issue.
		Error error = new Error();
		error.message = ex.getLocalizedMessage() + "Message: from server error handle";
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	//Test Method: SampleClient.test4()
	@ExceptionHandler({ SampleServerException1.class })
	public ResponseEntity<? extends Object> handleMyCustomBadRequest1(Exception ex, WebRequest request) {
		
		Error1 error = new Error1();
		error.message1 = ex.getLocalizedMessage() + "Second Message Exception test-2";//This is not accessible on the Java Spring Rest client.
		error.message = ex.getLocalizedMessage() + "Exception test-2";
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
