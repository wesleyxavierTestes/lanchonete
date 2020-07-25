package com.lanchonete.apllication.mappers;

import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.domain.entities.produto.entities.Produto;

final class ProdutoMapper {
    private ProdutoMapper() {}
    public static Produto update(ProdutoDto entityDto, Produto entity) {
        

        return entity;
    }
}