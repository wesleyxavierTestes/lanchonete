package com.lanchonete.domain.entities.cardapio.lanche;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ingrediente")
public class Ingrediente extends AbstractProduto implements IProdutoComposicao {

}