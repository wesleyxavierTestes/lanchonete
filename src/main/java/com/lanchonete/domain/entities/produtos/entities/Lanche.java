package com.lanchonete.domain.entities.produtos.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoComposicao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lanche extends AbstractProduto implements IProdutoCardapio {

    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    @JoinColumn( name = "lanche_id")
    private Set<IProdutoComposicao> composicao = new HashSet<IProdutoComposicao>();
}