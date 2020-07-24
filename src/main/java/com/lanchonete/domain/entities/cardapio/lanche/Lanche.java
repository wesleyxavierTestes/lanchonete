package com.lanchonete.domain.entities.cardapio.lanche;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoCombo;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoComposicao;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoPedido;

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
public class Lanche extends AbstractProduto implements IProdutoPedido, IProdutoCardapio, IProdutoCombo {

    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    private Set<IProdutoComposicao> ingredientesLanche = new HashSet<IProdutoComposicao>();
}