package com.mendusa.transactions.Exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//todo: delete unused codes

@Slf4j
@ControllerAdvice
public class GlobalExceptionMapper extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(ApiException.class)
//    public ResponseEntity<FxAppResponse<?>> handleEmptyInput (ApiException apiRequestException){
//        return new ResponseEntity<>(
//                new FxAppResponse<>(apiRequestException.getMessage(), Collections.singletonList(apiRequestException.getMessage())),
//                HttpStatus.BAD_REQUEST);
//    }
//
//
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleNoSuchElement (NoSuchElementException noSuchElementException){
//        return new ResponseEntity<String>("NO VALUE IS PRESENT IN THE DB", HttpStatus.NOT_FOUND);
//    }
//
//
//    @Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return new ResponseEntity<Object>("PLEASE CHANGE HTTP RETURN TYPE", HttpStatus.NOT_FOUND);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//        String responseDescription = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
//        FxAppResponse response = new FxAppResponse(-1, responseDescription);
//        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
//    }
}
