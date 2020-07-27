package com.lanchonete.apllication.dto.estoque;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.lanchonete.utils.MessageError;

public class EstoqueProdutoDto {

    @Min(value = 1, message = MessageError.IS_MANDATORY)
    public long id;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String nome;
}
