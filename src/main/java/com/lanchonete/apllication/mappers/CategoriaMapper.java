package com.lanchonete.apllication.mappers;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.domain.entities.categoria.Categoria;

final class CategoriaMapper {
    private CategoriaMapper() {}
    public static Categoria update(CategoriaDto entityDto, Categoria entity) {
        entity.setNome(entityDto.nome);
        return entity;
    }
}