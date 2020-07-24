package com.lanchonete.domain.entities.cardapio;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoCombo;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoPedido;

@Entity
public class Bebida extends AbstractProduto implements IProdutoPedido, IProdutoCardapio, IProdutoCombo {
    
}