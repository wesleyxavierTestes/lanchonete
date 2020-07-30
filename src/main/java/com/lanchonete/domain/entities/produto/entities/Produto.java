package com.lanchonete.domain.entities.produto.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.entities.estoque.IEstoque;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;

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
public class Produto extends AbstractProduto implements IProdutoCardapio, IProdutoPedido {

    /**
     * Estoques FindAllByProdutoId
     */
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, targetEntity = AbstractEstoque.class)
    private List<IEstoque> estoque;

    @Transient
    private double estoqueAtual;

    @Column(nullable = false)
    private BigDecimal custo;
}