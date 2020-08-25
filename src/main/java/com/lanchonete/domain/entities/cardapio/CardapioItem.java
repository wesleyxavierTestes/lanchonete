package com.lanchonete.domain.entities.cardapio;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CardapioItem extends AbstractProduto implements IProdutoCardapio {

}