package com.lanchonete.mocks.entities;

import java.math.BigDecimal;

import com.lanchonete.domain.entities.outros.Outros;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;

public class OutrosMock {

    public static Outros by(String nome) {
        Outros clienteDtoMock = new Outros();
        clienteDtoMock.setNome(nome);
        clienteDtoMock.setValor(new BigDecimal(22.5));
        clienteDtoMock.setCategoria(null);
        clienteDtoMock.setTipoProduto(EnumTipoProduto.Outros);
        return clienteDtoMock;
    }
}