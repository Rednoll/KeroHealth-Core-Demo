package com.kero.health.core.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;

@ControllerAdvice
public class ValidationErrorBuilder extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ValidationError error = ValidationErrorBuilder.createValidationError(exception.getBindingResult());
		
		return super.handleExceptionInternal(exception, error, headers, status, request);
	}
	
	public static ValidationError createValidationError(Errors errors) {
    
    	ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
		
		for(ObjectError objectError : errors.getAllErrors()) {
			
			error.addValidationError(objectError.getDefaultMessage());
		}
		
		return error;
    }
	
	public static class ValidationError {
	
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		private List<String> errors = new ArrayList<>();
		
		private final String errorMessage;
		
		public ValidationError(String errorMessage) {
			
			this.errorMessage = errorMessage;
		}
		
		public void addValidationError(String error) {
			
			errors.add(error);
		}
		
		public List<String> getErrors() {
			
			return errors;
		}
		
		public String getErrorMessage() {
			
			return errorMessage;
		}
	}
}