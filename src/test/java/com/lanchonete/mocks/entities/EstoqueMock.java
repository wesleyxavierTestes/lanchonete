package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;

public class EstoqueMock {

    public static EstoqueDto dto() {
        EstoqueDto clienteDtoMock = EstoqueDto.builder()

                .build();
        return clienteDtoMock;
    }

    public static EstoqueEntrada by() {
        EstoqueEntrada clienteDtoMock = new EstoqueEntrada();

        return clienteDtoMock;
    }
}