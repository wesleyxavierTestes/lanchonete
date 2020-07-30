package com.lanchonete.apllication.dto.cardapio;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.lanchonete.utils.MessageError;

public class CardapioItemDto {

    public long id;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String nome;

    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String codigo;   
}