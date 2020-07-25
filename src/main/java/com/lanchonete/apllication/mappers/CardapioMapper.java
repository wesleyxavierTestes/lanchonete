package com.lanchonete.apllication.mappers;

import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.domain.entities.cardapio.Cardapio;

final class CardapioMapper {
    private CardapioMapper() {}
    public static Cardapio update(CardapioDto entityDto, Cardapio entity) {

        return entity;
    }
}