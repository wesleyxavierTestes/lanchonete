package com.lanchonete.domain.entities.produto.baseentity;

import java.math.BigDecimal;

import com.lanchonete.domain.enuns.produto.EnumTipoProduto;

public interface IProdutoPedido extends IProduto {
    EnumTipoProduto getTipoProduto();
    void setValor(BigDecimal valor);
    void setNome(String nome);
}