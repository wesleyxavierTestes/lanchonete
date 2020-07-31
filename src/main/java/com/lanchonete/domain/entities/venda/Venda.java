package com.lanchonete.domain.entities.venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;

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
    
    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class,
    cascade = CascadeType.DETACH, mappedBy = "venda")
    private List<VendaItem> vendaItens = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente cliente;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private BigDecimal valorDesconto;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private boolean cancelado;

    @Column(nullable = true)
    private LocalDateTime dataCancelado;
}