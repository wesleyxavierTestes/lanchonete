package com.lanchonete.domain.entities.estoque;

import com.lanchonete.domain.entities.produto.entities.Produto;

public interface IEstoque {
    void configureSave();
    void setProduto(Produto produto);
}