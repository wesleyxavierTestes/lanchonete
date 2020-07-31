package com.lanchonete.domain.entities.produto.baseentity;

import java.util.UUID;

import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;

public interface IProdutoCardapio extends IProduto {
    long getId();
    UUID getCodigo();
    String getNome();
    EnumTipoProduto getTipoProduto();
    Categoria getCategoria();
}