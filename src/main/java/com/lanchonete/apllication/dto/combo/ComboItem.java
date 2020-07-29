package com.lanchonete.apllication.dto.combo;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.lanchonete.utils.MessageError;

public class ComboItem {

    public long id;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String nome;

    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String codigo;

    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String valor;
    
}