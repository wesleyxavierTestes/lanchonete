package com.lanchonete.mocks.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;

public class LancheMock {

    public static LancheDto dto() {

        CategoriaDto categoria = CategoriaDto.builder()
            .nome("Nova" + LocalDateTime.now().toString())
        .build();

        LancheDto clienteDtoMock = LancheDto.builder()
                .nome("hamburguer da semana"+ LocalDateTime.now().toString())
                .valor(new BigDecimal(22.5).toString())
                .valorTotal(new BigDecimal(22.5).toString())
                .categoria(categoria)
                .ingredientesLanche(null)
                .build();
        return clienteDtoMock;
    }

    public static Lanche by() {
        Lanche clienteDtoMock = new Lanche();

        return clienteDtoMock;
    }
}