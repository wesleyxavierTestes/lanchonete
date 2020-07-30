package com.lanchonete.apllication.dto.cardapio;

import java.util.List;

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

    public List<CardapioItemDto> itensDisponiveis;
}
