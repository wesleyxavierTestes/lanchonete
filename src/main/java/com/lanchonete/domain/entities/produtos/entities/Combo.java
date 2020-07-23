package com.lanchonete.domain.entities.produtos.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoCardapio;

@Entity
public class Combo  extends AbstractProduto implements IProdutoCardapio {
 
    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    private Set<IProduto> composicao = new HashSet<IProduto>();
}