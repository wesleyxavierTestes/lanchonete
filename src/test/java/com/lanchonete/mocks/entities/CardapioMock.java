package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.domain.entities.cardapio.Cardapio;

public class CardapioMock {

    public static CardapioDto dto(String nome) {
        CardapioDto clienteDtoMock = CardapioDto.builder().nome(nome).build();
        return clienteDtoMock;
    }

    public static Cardapio by(String nome) {
        Cardapio clienteDtoMock = new Cardapio();

        return clienteDtoMock;
    }
}