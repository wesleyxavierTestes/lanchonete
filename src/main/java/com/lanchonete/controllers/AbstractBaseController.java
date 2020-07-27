package com.lanchonete.controllers;

import com.lanchonete.apllication.validations.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractBaseController {
    @Autowired
    protected Validations validations;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handlerError(MethodArgumentNotValidException ex) {
        return validations.by(ex.getBindingResult().getTarget()).getErros();
    }
}