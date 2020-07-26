package com.lanchonete.apllication.dto.estoque;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDto  {

    public long id;

    @Size(min = 1)
    public long quantidade;
    public LocalDateTime data;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EstoqueProdutoDto produto;
}
