package com.lanchonete.domain.entities.venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoVenda;

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
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente cliente;

    private BigDecimal valorDesconto;
    private BigDecimal valor;
    private BigDecimal valorTotal;
    private boolean cancelado;
    private LocalDateTime dataCancelado;
}