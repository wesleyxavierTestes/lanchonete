package com.lanchonete.mocks.entities;

import java.math.BigDecimal;

import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;

public class BebidaMock {

    public static Bebida by(String nome) {
        Bebida clienteDtoMock = new Bebida();
        clienteDtoMock.setNome(nome);
        clienteDtoMock.setValor(new BigDecimal(22.5));
        clienteDtoMock.setCategoria(null);
        clienteDtoMock.setTipoProduto(EnumTipoProduto.Bebida);
        return clienteDtoMock;
    }
}