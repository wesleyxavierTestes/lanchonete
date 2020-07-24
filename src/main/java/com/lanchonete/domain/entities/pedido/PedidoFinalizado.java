package com.lanchonete.domain.entities.pedido;

import javax.persistence.Entity;

import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PedidoFinalizado extends Pedido {
    
    @Override
    public PedidoFinalizado configurar(EnumEstadoPedido estado) {
        this.setEstado(estado);
        return this;
    }
}