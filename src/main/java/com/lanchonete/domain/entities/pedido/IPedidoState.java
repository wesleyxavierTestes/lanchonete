package com.lanchonete.domain.entities.pedido;

import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

public interface IPedidoState {
    IPedidoState fazerPedido();
    IPedidoState cancelarPedido();
    IPedidoState finalizarPedido();

    IPedidoState configurar(EnumEstadoPedido estado);
}