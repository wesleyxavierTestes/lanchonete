package com.lanchonete.apllication.dto.cliente;

import javax.validation.constraints.Max;

import com.lanchonete.utils.MessageError;

public class ClienteGenericDto {

    public long id;
    public boolean ativo;
    
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String nome;
}
