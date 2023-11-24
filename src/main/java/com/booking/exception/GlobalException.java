package com.booking.exception;

import java.time.LocalDateTime;

import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetail> userExceptionHandler(UserException us, WebRequest req){
		
		ErrorDetail err= new ErrorDetail(us.getMessage(), req.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DriverException.class)
	public ResponseEntity<ErrorDetail> driverExceptionHandler(DriverException us, WebRequest req){
		
		ErrorDetail err= new ErrorDetail(us.getMessage(), req.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RideException.class)
	public ResponseEntity<ErrorDetail> rideExceptionHandler(RideException us, WebRequest req){
		
		ErrorDetail err= new ErrorDetail(us.getMessage(), req.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	
	//used in the email field can not be null in those places
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDetail> handleValidationExceptionHandler(ConstraintViolationException ex){
		StringBuffer errMessage = new StringBuffer();
		for(ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errMessage.append(violation.getMessage()+"\n");
			
			
		}
		
		
		ErrorDetail err= new ErrorDetail(errMessage.toString(),"Validation error",LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	
	
	//this used in password and mobile number that they should be of 9 to 10 lengths
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetail>methodArgumentNotValidExceptiomnHandler(MethodArgumentNotValidException us, WebRequest req){
		
		ErrorDetail err= new ErrorDetail(us.getBindingResult().getFieldError().getDefaultMessage(), req.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	} 
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception us, WebRequest req){
		
		ErrorDetail err= new ErrorDetail(us.getMessage(), req.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	
}
