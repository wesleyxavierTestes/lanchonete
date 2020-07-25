package com.lanchonete.apllication.dto.cardapio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardapioDto  {

    public long id;
    public String nome;
}
