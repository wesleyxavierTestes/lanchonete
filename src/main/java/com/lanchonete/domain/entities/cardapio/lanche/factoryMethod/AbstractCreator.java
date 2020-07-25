package com.lanchonete.domain.entities.cardapio.lanche.factoryMethod;

import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;

public abstract class AbstractCreator {
    
    public abstract IProdutoComposicao FactoryMethod();
}