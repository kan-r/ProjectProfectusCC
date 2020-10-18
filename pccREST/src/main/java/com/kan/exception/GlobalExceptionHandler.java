package com.kan.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Object> handleInvalidDataException(InvalidDataException e){
		logger.warn("InvalidDataException", e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleRuntimeException(RuntimeException e){
		logger.warn("RuntimeException", e.getMessage());
		 return ResponseEntity.badRequest().body(ExceptionUtils.getRootCauseMessage(e));
	}
}
