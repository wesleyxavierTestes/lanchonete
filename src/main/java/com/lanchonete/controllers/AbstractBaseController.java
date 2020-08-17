package com.lanchonete.controllers;

import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.validations.Validations;
import com.lanchonete.utils.MessageError;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String regraNegocioException(RegraNegocioException ex) {
        return ex.getMessage();
    }   

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String dataIntegrityViolationException(DataIntegrityViolationException ex)  {
        
        String nomeDoTipoException = ex.getCause().getClass().getTypeName();
        String nomeDoTipoErrorBancoDeDados = ConstraintViolationException.class.getTypeName();

        if (nomeDoTipoException.equals(nomeDoTipoErrorBancoDeDados) 
        && ex.getCause().getCause().getMessage().toLowerCase().contains("unique")) {
            return "Duplicação de item não permitida no banco";
        }
        return MessageError.ERROS_DATABASE;
    }
}