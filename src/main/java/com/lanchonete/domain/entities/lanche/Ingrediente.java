package com.lanchonete.domain.entities.lanche;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ingrediente")
public class Ingrediente extends AbstractProduto implements IProdutoComposicao {

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Lanche lanche;
}