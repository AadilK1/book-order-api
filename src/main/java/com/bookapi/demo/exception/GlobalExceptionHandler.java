package com.bookapi.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{
	
	  @ExceptionHandler(ResourceNotFoundException.class)
	  public ResponseEntity<Object> handleNotFoundException(
			  ResourceNotFoundException resourceNotFoundException, 
	      WebRequest request
	  ){
		    
		    return buildErrorResponse(resourceNotFoundException, HttpStatus.NOT_FOUND, request);
	       
	  }
	  
	  @ExceptionHandler(RuntimeException.class)
	  public ResponseEntity<Object> handleRuntimeException(
			  Exception runtimeExecption, 
	      WebRequest request
	  ){
		    
		    return buildErrorResponse(runtimeExecption, HttpStatus.INTERNAL_SERVER_ERROR, request);
	       
	  }

	private ResponseEntity<Object> buildErrorResponse(Exception exception,
			HttpStatus httpStatus, WebRequest request) {

	    ApiError errorResponse = new ApiError(
	            httpStatus.value(), 
	            exception.getMessage()
	        );
	    return ResponseEntity.status(httpStatus).body(errorResponse);
	}
	
}
