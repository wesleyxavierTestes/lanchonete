package com.lanchonete.domain.entities.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;
import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pedido extends BaseEntity implements IPedidoState {
    
    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class,
    cascade = CascadeType.DETACH)
    private List<IProdutoPedido> pedidoitens = new ArrayList<>();

    private UUID codigo;

    private BigDecimal valor;
    private BigDecimal valorDesconto;
    private BigDecimal valorTotal;
    
    private boolean cancelado;
    private LocalDateTime dataCancelado;

    @Enumerated(EnumType.STRING)
    private EnumEstadoPedido estado = EnumEstadoPedido.Novo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL)
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

    public void calcularValorTotal() {
        this.setValorTotal(this.getValor().subtract(this.getValorDesconto()));
    }
}