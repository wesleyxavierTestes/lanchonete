package com.lanchonete.mocks.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.produto.entities.Produto;

public class ProdutoMock {
    
    public static ProdutoDto dto() {
            ProdutoDto clienteDtoMock = ProdutoDto.builder()
                                .categoria(new CategoriaDto(1, "Teste"))
                                .custo("10.1")
                                .valor("10.1")
                                .id(0)
                                .nome("Teste" + LocalDateTime.now().toString())
                                .build();
                                    return clienteDtoMock;
    }

    public static Produto by() {
        Produto clienteDtoMock = new Produto();

                clienteDtoMock.setCusto(new BigDecimal("10.1"));
                clienteDtoMock.setValor(new BigDecimal("10.1"));
                clienteDtoMock.setNome("Teste" + LocalDateTime.now().toString());
                clienteDtoMock.setCategoria(new Categoria("Teste"));
                                return clienteDtoMock;
    }
}