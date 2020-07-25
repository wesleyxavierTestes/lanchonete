package com.lanchonete.domain.entities.pedido;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;
import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pedido extends BaseEntity implements IPedidoState {    
    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    private Set<IProdutoPedido> ItemsVenda = new HashSet<IProdutoPedido>();

    private BigDecimal valorDesconto;
    private BigDecimal valorTotal;
    private EnumEstadoPedido estado = EnumEstadoPedido.Realizando;

    @Override
    public IPedidoState fazerPedido() {
       return Mapper.map(this, PedidoAguardando.class)
        .configurar(EnumEstadoPedido.Aguardando);
    }

    @Override
    public IPedidoState cancelarPedido() {
        return Mapper.map(this, PedidoCancelamento.class)
        .configurar(EnumEstadoPedido.Cancelado);
    }

    @Override
    public IPedidoState finalizarPedido() {
        return Mapper.map(this, PedidoFinalizado.class)
        .configurar(EnumEstadoPedido.Finalizado);
    }
}