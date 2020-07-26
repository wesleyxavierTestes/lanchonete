package com.lanchonete.domain.entities.pedido;

import javax.persistence.Entity;

import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PedidoCancelamento extends Pedido {
    
    @Override
    public PedidoCancelamento configurar() {
        this.configurarEstadoPedido(EnumEstadoPedido.Cancelado);
        this.setAtivo(false);
        return this;
    }
}