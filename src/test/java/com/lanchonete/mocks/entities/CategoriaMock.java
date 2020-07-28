package com.lanchonete.mocks.entities;

import java.time.LocalDateTime;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.domain.entities.categoria.Categoria;


public class CategoriaMock {

    public static CategoriaDto dto() {

        CategoriaDto clienteDtoMock = CategoriaDto.builder()
            .nome("Nova" + LocalDateTime.now().toString())
        .build();

        return clienteDtoMock;
    }

    public static Categoria by() {
        Categoria clienteDtoMock = new Categoria();
        clienteDtoMock.setNome("Nova" + LocalDateTime.now().toString());
        return clienteDtoMock;
    }
}