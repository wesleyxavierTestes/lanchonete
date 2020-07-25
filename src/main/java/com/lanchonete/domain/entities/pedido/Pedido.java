package com.lanchonete.domain.entities.pedido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

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
    
    @NotNull(message = "Itens são obrigatório")
    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    private Set<IProdutoPedido> ItensVenda = new HashSet<IProdutoPedido>();

    private BigDecimal valorDesconto;
    private BigDecimal valorTotal;

    private EnumEstadoPedido estado = EnumEstadoPedido.Novo;

    @OneToMany
    private List<EstadoPedido> estadosPedido;

    protected void configurarEstadoPedido(EnumEstadoPedido estado) {
        this.setEstado(estado);

        if (!Objects.nonNull(this.estadosPedido))
            this.estadosPedido = new ArrayList<>();
            
        this.estadosPedido.add(new EstadoPedido(this.getEstado()));
    }

    @Override
    public IPedidoState fazerPedido() {
        return Mapper.map(this, PedidoAguardando.class).configurar();
    }

    @Override
    public IPedidoState cancelarPedido() {
        return Mapper.map(this, PedidoCancelamento.class).configurar();
    }

    @Override
    public IPedidoState finalizarPedido() {
        return Mapper.map(this, PedidoFinalizado.class).configurar();
    }
}