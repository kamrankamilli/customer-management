package com.enoca.app.exception;


import com.enoca.app.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, new HttpHeaders(), "Bad Request", ex.getMessage(), null, webRequest.getDescription(false));
        return errorResponse.sendResponse();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, new HttpHeaders(), "Resource not found", ex.getMessage(), null, webRequest.getDescription(false));
        return errorResponse.sendResponse();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, new HttpHeaders(), "Illegal Argument", ex.getMessage(), null, webRequest.getDescription(false));
        return errorResponse.sendResponse();
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, HttpStatusCode statusCode, WebRequest webRequest) {
        List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                    + violation.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(statusCode, new HttpHeaders(), null, null, errors, webRequest.getDescription(false));
        return errorResponse.sendResponse();
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAll(Exception ex, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, new HttpHeaders(), "Internal Server Error", "Something went wrong, try again later", null, webRequest.getDescription(false));
//        return errorResponse.sendResponse();
//    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));
        ErrorResponse errorResponse = new ErrorResponse(status, headers, "HTTP Request Method Not Supported", builder.toString(), null, request.getDescription(false));
        return errorResponse.sendResponse();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));
        ErrorResponse errorResponse = new ErrorResponse(status, headers, "HTTP Media Type Not Supported", builder.substring(0, builder.length() - 2), null, request.getDescription(false));
        return errorResponse.sendResponse();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getParameterName() + " parameter is missing";
        ErrorResponse errorResponse = new ErrorResponse(status, headers, "Missing Request Parameter", message, null, request.getDescription(false));
        return errorResponse.sendResponse();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getRequestPartName() + " part is missing";
        ErrorResponse errorResponse = new ErrorResponse(status, headers, "Missing Request Part", message, null, request.getDescription(false));
        return errorResponse.sendResponse();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = getErrors(ex);
        ErrorResponse errorResponse = new ErrorResponse(status, headers, "Method Argument Not Valid", null, errors, request.getDescription(false));
        return errorResponse.sendResponse();
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ErrorResponse errorResponse = new ErrorResponse(status, headers, "No Handler", message, null, request.getDescription(false));
        return errorResponse.sendResponse();
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        ErrorResponse errorResponse = new ErrorResponse(status, new HttpHeaders(), "No Handler", message, null, request.getDescription(false));
        return errorResponse.sendResponse();
    }

    private List<String> getErrors(BindException ex) {
        List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + " " + error.getDefaultMessage());
        }
        return errors;
    }
}
