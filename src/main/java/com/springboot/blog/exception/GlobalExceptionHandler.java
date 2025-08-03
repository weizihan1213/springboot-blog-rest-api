package com.springboot.blog.exception;

import com.springboot.blog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Exception Handler for "ResourceNotFoundException"
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = this.buildErrorDetails(exception, webRequest);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Exception Handler for "BlogAPIException"
    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = this.buildErrorDetails(exception, webRequest);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Exception Handler for Global Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(Exception exception, WebRequest webRequest) {
        ErrorDetails errorDetails = this.buildErrorDetails(exception, webRequest);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Build ErrorDetails with local timestamp, message, and URI info
    private ErrorDetails buildErrorDetails(Exception exception, WebRequest webRequest) {
        // Convert the timestamp to formatted local time
        Date date = new Date();
        ZonedDateTime localTime = date.toInstant().atZone(ZoneId.systemDefault());
        String formattedTime = localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));

        return new ErrorDetails(formattedTime, exception.getMessage(), webRequest.getDescription(false));
    }
}
