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

    @Min(value = 0, message = MessageError.MIN_LIMITE)
    public long quantidade;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String data;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EstoqueProdutoDto produto;
}
