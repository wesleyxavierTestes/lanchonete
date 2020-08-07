package com.lanchonete.apllication.dto.cliente;

import javax.validation.constraints.NotNull;

import com.lanchonete.utils.MessageError;

public class ClienteGenericDto {

    @NotNull(message = MessageError.IS_MANDATORY)
    public long id;
    public boolean ativo;
    
    public String nome;
}
