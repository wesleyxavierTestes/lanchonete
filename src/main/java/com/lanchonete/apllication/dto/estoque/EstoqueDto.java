package com.lanchonete.apllication.dto.estoque;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDto  {

    public long id;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Min(value = 1, message = MessageError.MIN_LIMITE)
    public Long quantidade;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EstoqueProdutoDto produto;
}
