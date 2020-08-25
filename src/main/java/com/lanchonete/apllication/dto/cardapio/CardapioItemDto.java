package com.lanchonete.apllication.dto.cardapio;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.utils.MessageError;

public class CardapioItemDto {

    public long id;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String nome;

    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String codigo;
    
    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumTipoProduto tipoProduto;
}