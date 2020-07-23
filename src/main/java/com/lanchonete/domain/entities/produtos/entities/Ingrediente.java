package com.lanchonete.domain.entities.produtos.entities;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoComposicao;

import lombok.*;

@Builder
@Data
@Entity
public class Ingrediente  extends AbstractProduto implements IProdutoComposicao {
    
}