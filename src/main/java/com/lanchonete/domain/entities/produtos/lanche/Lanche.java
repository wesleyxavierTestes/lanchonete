package com.lanchonete.domain.entities.produtos.lanche;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoCombo;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoComposicao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lanche extends AbstractProduto implements IProdutoCardapio, IProdutoCombo {

    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    private Set<IProdutoComposicao> ingredientes = new HashSet<IProdutoComposicao>();
}