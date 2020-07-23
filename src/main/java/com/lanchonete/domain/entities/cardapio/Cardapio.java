package com.lanchonete.domain.entities.cardapio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoCardapio;

@Entity
public class Cardapio {
    @Id @GeneratedValue
    private Long id;

    @OneToMany(targetEntity = AbstractProduto.class)
    @JoinColumn(name = "cardapio_id")
    private Set<IProdutoCardapio> lanches = new HashSet<IProdutoCardapio>();
}