package com.lanchonete.domain.entities.pedidos;

import javax.persistence.Entity;

import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PedidoCancelamento extends Pedido {
    
    @Override
    public PedidoCancelamento configurar(EnumEstadoPedido estado) {
        this.setEstado(estado);
        return this;
    }
}