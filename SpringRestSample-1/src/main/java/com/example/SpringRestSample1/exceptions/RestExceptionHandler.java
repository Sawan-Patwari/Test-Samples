package com.example.SpringRestSample1.exceptions;

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

	@ExceptionHandler({ SampleException.class })
	public ResponseEntity<? extends Object> handleBadRequest(Exception ex, WebRequest request) {

		ResponseEntity.BodyBuilder x = ResponseEntity.status(HttpStatus.BAD_REQUEST);
		// x.body((SampleException)ex);
		SampleException m = new SampleException();
		return x.body(m);

	}

}
