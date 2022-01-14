package com.usermanagement.Usermanagement.exception.usernameAlreadyExists;

import com.usermanagement.Usermanagement.exception.badRequest.BadRequestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UsernameAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus
    String usernameAlreadyExistsHandler(UsernameAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
