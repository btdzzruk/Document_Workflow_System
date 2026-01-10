package com.example.exception;

import com.example.dto.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIResponse<Object>> handleNotFoundException(NotFoundException e) {
        APIResponse<Object> response = new APIResponse<>();
        response.setSuccess(false);
        response.setMessage(e.getMessage());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<APIResponse<Object>> handleBusinessException(BusinessException e) {
        APIResponse<Object> response = new APIResponse<>();
        response.setSuccess(false);
        response.setMessage(e.getMessage());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {

        String msg = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        APIResponse<Object> response = new APIResponse<>();
        response.setSuccess(false);
        response.setMessage(msg);
        response.setData(null);
        return ResponseEntity.badRequest().body(response);
    }

}