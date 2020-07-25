package com.lanchonete.domain.entities.cardapio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;

@Entity
public class Cardapio extends BaseEntity {

    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    @Column(name = "items_disponiveis")
    private Set<IProdutoCardapio> itemsDisponiveis = new HashSet<IProdutoCardapio>();
}