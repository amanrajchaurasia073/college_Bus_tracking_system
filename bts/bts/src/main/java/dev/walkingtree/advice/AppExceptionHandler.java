package dev.walkingtree.advice;

import dev.walkingtree.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AppExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex)
    {
        Map<String,String> errorMessage = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMessage.put(error.getField(),error.getDefaultMessage());
        });
        return errorMessage;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String,String> handleBusinessException(ResourceNotFoundException userNotFoundException)
    {
        Map<String ,String> errorMap = new HashMap<>();
        errorMap.put("error message",userNotFoundException.getMessage());
        return errorMap;
    }
}