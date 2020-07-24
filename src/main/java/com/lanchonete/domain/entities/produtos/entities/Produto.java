package com.lanchonete.domain.entities.produtos.entities;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProduto;

@Entity
public class Produto extends AbstractProduto implements IProduto {

    
}