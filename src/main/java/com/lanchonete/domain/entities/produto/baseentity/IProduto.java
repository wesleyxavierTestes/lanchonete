package com.lanchonete.domain.entities.produto.baseentity;

import java.math.BigDecimal;
import java.util.UUID;

import com.lanchonete.domain.enuns.produto.EnumTipoProduto;

public interface IProduto {
    BigDecimal getValor();
    String getNome();
    UUID getCodigo();
    void setCodigo(UUID codigo);
    IProduto setTipoProduto(EnumTipoProduto tipoProduto);
}