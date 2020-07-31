package com.lanchonete.domain.entities.bebida;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCombo;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bebida extends AbstractProduto implements IProdutoPedido, IProdutoCardapio, IProdutoCombo {
    public Bebida() {
    }
}