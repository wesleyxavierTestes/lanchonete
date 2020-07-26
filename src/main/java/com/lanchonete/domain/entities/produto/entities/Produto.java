package com.lanchonete.domain.entities.produto.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.entities.estoque.IEstoque;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;

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
public class Produto extends AbstractProduto implements IProduto {

    /**
     * Estoques FindAllByProdutoId
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AbstractEstoque.class)
    private List<IEstoque> estoque;

    @Column(nullable = false)
    private BigDecimal custo;
}