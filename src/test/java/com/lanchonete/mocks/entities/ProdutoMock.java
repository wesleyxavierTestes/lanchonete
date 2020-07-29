package com.lanchonete.mocks.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.domain.entities.produto.entities.Produto;

public class ProdutoMock {
    
    public static ProdutoDto dto(String nome) {
            ProdutoDto clienteDtoMock = ProdutoDto.builder()
                                .categoria(null)
                                .custo("10.1")
                                .valor("10.1")
                                .id(0)
                                .nome(nome)
                                .build();
                                    return clienteDtoMock;
    }

    public static Produto by(String nome) {
        Produto clienteDtoMock = new Produto();

                clienteDtoMock.setCusto(new BigDecimal("10.1"));
                clienteDtoMock.setValor(new BigDecimal("10.1"));
                clienteDtoMock.setNome(nome);
                clienteDtoMock.setCategoria(null);
                                return clienteDtoMock;
    }
}