package com.lanchonete.domain.entities.produtos.entities;

import java.util.Set;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProduto;

import lombok.*;

@Builder
@Data
@Entity
public class Venda extends AbstractProduto implements IProduto {

    
}