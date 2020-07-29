package com.lanchonete.mocks.entities;

import java.math.BigDecimal;

import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.domain.entities.cardapio.combo.Combo;

public class ComboMock {

    public static ComboDto dto(String nome) {

        ComboDto clienteDtoMock = ComboDto.builder()
                .nome(nome)
                .valor(new BigDecimal(22.5).toString())
                .valorTotal(new BigDecimal(22.5).toString())
                .categoria(null)
                .lanche(null)
                .build();
        return clienteDtoMock;
    }

    public static Combo by(String nome) {
        Combo clienteDtoMock = new Combo();
        clienteDtoMock.setNome(nome);
        clienteDtoMock.setValor(new BigDecimal(22.5));
        clienteDtoMock.setValorTotal(new BigDecimal(22.5));
        clienteDtoMock.setCategoria(null);
        clienteDtoMock.setLanche(null);
        return clienteDtoMock;
    }
}