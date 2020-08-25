package com.lanchonete.apllication.dto.cardapio;

import java.util.List;

import javax.validation.constraints.Size;

import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardapioListDto {

    public long id;
    public boolean ativo;
    public String nome;

    @Size(min = 1, message = MessageError.IS_MANDATORY)
    public List<CardapioItemDto> itensDisponiveis;
}
