package com.lanchonete.domain.entities.venda;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VendaItem extends AbstractProduto {

    @OneToOne(fetch = FetchType.LAZY)
    private Pedido pedido;

    @Column(nullable = true)
    private BigDecimal valorDesconto;

    @Column(nullable = false)
    private BigDecimal valorTotal;
}