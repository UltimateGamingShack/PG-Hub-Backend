package com.pghub.user.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.pghub.user.exception.PgHubException;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AccountStatusException;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandling {
	@Autowired
	Environment environment;
	
	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
	public ResponseEntity<ErrorInfo> ServiceClassException(Exception e){
		String errorMsg;
        if(e instanceof MethodArgumentNotValidException manvException){
            errorMsg = manvException.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
        }else{
            ConstraintViolationException cvException = (ConstraintViolationException) e;
            errorMsg = cvException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));

        }

        ErrorInfo err = new ErrorInfo();
		err.setErrorCode(HttpStatus.BAD_REQUEST.value());
		err.setErrorMessage(errorMsg);
		err.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorInfo>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalException(Exception e){
		ErrorInfo err = new ErrorInfo();
		err.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		err.setErrorMessage(environment.getProperty("General.MESSAGE"));
		err.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorInfo>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(PgHubException.class)
	public ResponseEntity<ErrorInfo> PgHubExceptionHandler(PgHubException ngae){
		ErrorInfo err = new ErrorInfo();
		err.setErrorCode(HttpStatus.NOT_FOUND.value());
		err.setErrorMessage(environment.getProperty(ngae.getMessage()));
		err.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorInfo>(err, HttpStatus.NOT_FOUND);
	}
	
	
	
//	@ExceptionHandler({BadCredentialsException.class, AccountStatusException.class, AccessDeniedException.class, SignatureException.class, ExpiredJwtException.class})
//    public ProblemDetail handleSecurityException(Exception exception) {
//        ProblemDetail errorDetail = null;
//
////        exception.printStackTrace();
//
//        if (exception instanceof BadCredentialsException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
//            errorDetail.setProperty("description", "The username or password is incorrect");
//
//            return errorDetail;
//        }
//
//        if (exception instanceof AccountStatusException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "The account is locked");
//        }
//
//        if (exception instanceof AccessDeniedException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "You are not authorized to access this resource");
//        }
//
//        if (exception instanceof SignatureException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "The JWT signature is invalid");
//        }
//
//        if (exception instanceof ExpiredJwtException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "The JWT token has expired");
//        }
//
//        if (errorDetail == null) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
//            errorDetail.setProperty("description", "Unknown internal server error.");
//        }
//
//        return errorDetail;
//    }
//
}
