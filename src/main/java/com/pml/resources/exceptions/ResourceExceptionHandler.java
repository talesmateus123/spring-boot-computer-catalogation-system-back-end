package com.pml.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pml.services.exceptions.ConflictOfObjectsException;
import com.pml.services.exceptions.DataIntegrityException;
import com.pml.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		// The Http status
		HttpStatus status = HttpStatus.NOT_FOUND;
		// The standard error
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegity(DataIntegrityException e, HttpServletRequest request){
		// The Http status
		HttpStatus status = HttpStatus.BAD_REQUEST;
		// The standard error
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ConflictOfObjectsException.class)
	public ResponseEntity<StandardError> ConflictOfObjects(ConflictOfObjectsException e, HttpServletRequest request){
		// The Http status
		HttpStatus status = HttpStatus.CONFLICT;
		// The standard error
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}
}
