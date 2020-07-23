package com.lanchonete.domain.entities.produtos.entities;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProduto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
public class Produto extends AbstractProduto implements IProduto {

    
}