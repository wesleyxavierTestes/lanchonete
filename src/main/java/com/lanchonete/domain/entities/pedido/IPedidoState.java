package com.lanchonete.domain.entities.pedido;

public interface IPedidoState {
    IPedidoState fazerPedido();
    IPedidoState cancelarPedido();
    IPedidoState finalizarPedido();

    IPedidoState configurar();
}