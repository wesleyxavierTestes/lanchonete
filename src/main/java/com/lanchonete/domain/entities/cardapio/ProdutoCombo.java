package com.lanchonete.domain.entities.cardapio;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCombo;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;

@Entity
public class ProdutoCombo extends AbstractProduto implements IProdutoPedido, IProdutoCardapio, IProdutoCombo {

}