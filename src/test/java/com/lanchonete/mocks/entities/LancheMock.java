package com.lanchonete.mocks.entities;

import java.math.BigDecimal;

import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;

public class LancheMock {

    public static LancheDto dto(String nome) {

        LancheDto clienteDtoMock = LancheDto.builder()
                .nome(nome)
                .valor(new BigDecimal(22.5).toString())
                .valorTotal(new BigDecimal(22.5).toString())
                .categoria(null)
                .ingredientesLanche(null)
                .build();
        return clienteDtoMock;
    }

    public static Lanche by(String nome) {
        Lanche clienteDtoMock = new Lanche();
        clienteDtoMock.setNome(nome);
        clienteDtoMock.setValor(new BigDecimal(22.5));
        clienteDtoMock.setValorTotal(new BigDecimal(22.5));
        clienteDtoMock.setCategoria(null);
        clienteDtoMock.setIngredientesLanche(null);
        return clienteDtoMock;
    }
}