package com.lanchonete.domain.entities.pedido;

import javax.persistence.Entity;

import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PedidoNovo extends Pedido {
    
    @Override
    public PedidoNovo configurar() {
        this.configurarEstadoPedido(EnumEstadoPedido.Novo);
        return this;
    }
}