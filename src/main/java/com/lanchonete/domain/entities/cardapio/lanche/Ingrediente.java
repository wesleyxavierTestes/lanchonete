package com.lanchonete.domain.entities.cardapio.lanche;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoComposicao;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ingrediente")
@Polymorphism(type = PolymorphismType.IMPLICIT)
public class Ingrediente extends AbstractProduto implements IProdutoComposicao {
    
    private String observacao;
}