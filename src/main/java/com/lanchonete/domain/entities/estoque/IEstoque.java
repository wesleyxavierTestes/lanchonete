package com.lanchonete.domain.entities.estoque;

import com.lanchonete.domain.entities.produto.Produto;

public interface IEstoque {
    void configureSave();
    void setProduto(Produto produto);
}