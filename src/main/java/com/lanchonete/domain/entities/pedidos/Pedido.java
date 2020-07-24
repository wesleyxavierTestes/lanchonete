package com.lanchonete.domain.entities.pedidos;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido extends BaseEntity {
    
    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    private Set<IProdutoPedido> ItemsVenda = new HashSet<IProdutoPedido>();

    private BigDecimal valorDesconto;
    private BigDecimal valorTotal;
}