package com.lanchonete.apllication.dto.cardapio;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardapioDto  {

    public long id;
    public boolean ativo;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String nome;

    public List<CardapioItemDto> itensDisponiveis;
}
