package com.songify.apivalidation;

import com.songify.song.infrastructure.controller.SongsController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(assignableTypes = SongsController.class)
public class ApiValidationErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiVaValidationErrorResponseDto handleValidationException(MethodArgumentNotValidException exception){
        List<String> messages = getErrorsFromException(exception);
        return new ApiVaValidationErrorResponseDto(messages,HttpStatus.BAD_REQUEST);
    }
    private List<String> getErrorsFromException(MethodArgumentNotValidException exception){
        return exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
