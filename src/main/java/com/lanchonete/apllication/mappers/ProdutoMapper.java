package com.lanchonete.apllication.mappers;

import java.math.BigDecimal;

import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.domain.entities.produto.Produto;

final class ProdutoMapper {
    private ProdutoMapper() {}
    public static Produto update(ProdutoDto entityDto, Produto entity) {
        entity.setNome(entityDto.nome);
        entity.setValor(new BigDecimal(entityDto.valor));
        entity.setCusto(new BigDecimal(entityDto.custo));
        entity.setCategoria(CategoriaMapper
        .update(entityDto.categoria, entity.getCategoria()));

        return entity;
    }
}