package com.lanchonete.domain.entities.venda;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoVenda;

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
public class Venda extends BaseEntity {
    
    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    private Set<IProdutoVenda> ItemsVenda = new HashSet<IProdutoVenda>();

    private BigDecimal valorDesconto;
    private BigDecimal valorTotal;
}