package com.lanchonete.domain.entities.produto.baseentity;

import java.math.BigDecimal;
import java.util.UUID;

public interface IProduto {
    BigDecimal getValor();
    UUID getCodigo();
}