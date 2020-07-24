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
import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;
import com.lanchonete.utils.ModelMapperUtils;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class Pedido extends BaseEntity implements IPedidoState {
    private ModelMapper mapper = ModelMapperUtils.getInstance();
    
    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    private Set<IProdutoPedido> ItemsVenda = new HashSet<IProdutoPedido>();

    private BigDecimal valorDesconto;
    private BigDecimal valorTotal;
    private EnumEstadoPedido estado = EnumEstadoPedido.Realizando;

    @Override
    public IPedidoState fazerPedido() {
       return mapper.map(this, PedidoAguardando.class)
        .configurar(EnumEstadoPedido.Aguardando);
    }

    @Override
    public IPedidoState cancelarPedido() {
        return mapper.map(this, PedidoCancelamento.class)
        .configurar(EnumEstadoPedido.Cancelado);
    }

    @Override
    public IPedidoState finalizarPedido() {
        return mapper.map(this, PedidoFinalizado.class)
        .configurar(EnumEstadoPedido.Finalizado);
    }
}