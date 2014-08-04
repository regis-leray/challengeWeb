package org.rayjars.appdirect.web;

import org.rayjars.appdirect.exceptions.AppException;
import org.rayjars.appdirect.xml.beans.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result handleValidationError(AppException ex) {
        return ResponseHelper.error(ex.getMessage(), ex.getCode());
    }

}
