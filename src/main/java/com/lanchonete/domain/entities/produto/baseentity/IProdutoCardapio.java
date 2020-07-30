package com.lanchonete.domain.entities.produto.baseentity;

import java.util.UUID;

public interface IProdutoCardapio extends IProduto {
    long getId();
    UUID getCodigo();
}