package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.domain.entities.categoria.Categoria;


public class CategoriaMock {

    public static CategoriaDto dto(String nome) {

        CategoriaDto clienteDtoMock = CategoriaDto.builder()
            .nome(nome)
        .build();

        return clienteDtoMock;
    }

    public static Categoria by(String nome) {
        Categoria clienteDtoMock = new Categoria();
        clienteDtoMock.setNome(nome);
        return clienteDtoMock;
    }
}