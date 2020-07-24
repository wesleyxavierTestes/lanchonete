package com.lanchonete.domain.entities.produtos.lanche;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoComposicao;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

@Entity
@Polymorphism(type = PolymorphismType.IMPLICIT)
public class Ingrediente extends AbstractProduto implements IProdutoComposicao {
    
}